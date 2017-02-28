"use strict";

angular.module('Generic')
    .factory('Generic.Service.Generic', ['Generic.HttpDataAdapter', '$q', '$timeout', function (DataAdapter, $q, $timeout) {
        function GenericManager() {
            this.$filter = [];
        };

        GenericManager.__asMethod__ = function (func, superClass) {
            return function () {
                var currentSuperClass = this.$;
                this.$ = superClass;
                var ret = func.apply(this, arguments);
                this.$ = currentSuperClass;
                return ret;
            };
        };

        GenericManager.extend = function (className, def) {
            function classDef() {
                this.construct.apply(this, arguments);
            };

            angular.copy(this.prototype, classDef.prototype);
            angular.extend(classDef.prototype, def);

            classDef.extend = this.extend;
            classDef.prototype.$ = this.prototype;

            return classDef;
        };

        GenericManager.prototype = {
            $model: null,
            $pool: {},
            $result: [],
            $filter: [],
            $resource: {
                load: {
                    action: ''
                },
                remove: {
                    action: ''
                }
            },
            $adapter: {},
            $total: 0,
            construct: function (collection) {
                this.$model = collection || this.$model;
                
                if (!angular.isDefined(this.$model.prototype.getTypeName)){
                    throw new Error('A coleção do Service não foi definido.');
                }
                
                this.$adapter = new DataAdapter(this.$model);

                this.$filter = [];
                this.$pool = {};
                this.$result = [];
                this.$total = 0;
            },
            $retrieveInstance: function (id, data, toSave) {
                var instance = {},
                    dT = new Date(),
                    i = 0,
                    key = dT.toISOString(),
                    idClean = null;

                toSave = toSave === undefined ? true : toSave;

                if (id === null) {
                    instance = new this.$model;
                } else {
                    instance = this.$search(id);
                }

                if (angular.isDefined(data.getTypeName)) {
                    if (data.getTypeName() === this.$model.prototype.getTypeName()) {
                        instance = data;
                    }
                } else if (angular.isObject(data)) {
                    instance.set(data);
                }

                idClean = (new this.$model).getPrimaryKey();
                
                if (angular.isObject(idClean)) {
                    $.each(idClean, function (k, v) {
                        idClean[k] = String(v);
                    });
                }
                
                idClean = angular.toJson(idClean);
                id = instance.getPrimaryKey();
                
                if (angular.isObject(id)) {
                    $.each(id, function (k, v) {
                        id[k] = String(v);
                    });
                }
                
                id = angular.toJson(id);
                id = idClean === id ? null : id;

                if (toSave && id === null) {
                    while (true) {
                        if (!this.$pool[key + i]) {
                            key += i;
                            break;
                        } else {
                            i++;
                        }
                    }
                    
                    this.$pool[key] = {
                        object: instance,
                        toSave: toSave
                    };

                    this.$result[this.$result.length] = instance;
                } else {
                    if (this.$search(id)) {
                        this.$pool[id].toSave = true;
                        this.$pool[id].object.set(instance.convertToObject());
                    } else {
                        this.$pool[id] = {
                            object: instance,
                            toSave: toSave
                        };
                        
                        this.$result[this.$result.length] = instance;
                    }
                }

                return instance;
            },
            $search: function (id) {
                if (angular.isObject(id)) {
                    $.each(id, function (k, v) {
                        id[k] = String(v);
                    });
                }
                
                if (angular.isDefined(this.$pool[angular.toJson(id)])){
                    return this.$pool[angular.toJson(id)].object;
                } else {
                    return null;
                }
            },
            add: function (data) {
                if (angular.isArray(data)) {
                    this.$total += data.length;
                    
                    for (var k in data) {
                        this.$retrieveInstance(null, data[k]);
                    }
                } else if (angular.isObject(data)) {
                    this.total++;
                    this.$retrieveInstance(null, data);
                }
            },
            clear: function () {
                var service = this;
                
                for (var k in service.$pool) {
                    delete service.$pool[k];
                }
                
                service.$result = [];
                this.$total = 0;
            },
            create: function () {
                return new this.$model;
            },
            convertToArray: function (fields) {
                var list = [];
                
                for (var k in this.$result) {
                    if (angular.isDefined(this.$result[k].getTypeName))
                        list.push(fields ? this.$result[k].convertObjectToFields() : this.$result[k].convertToObject());
                }
                
                return list;
            },
            filter: function (data) {
                var filter = [];
                
                angular.extend(this.$filter, data);
                angular.copy(this.$filter, filter);
                
                return filter;
            },
            find: function (data) {
                var service = this,
                    total = 0;

                data = data || {data: []};
                data.data = this.$model.prototype.$convertObjectToFields(service.$filter.concat(data.data ? data.data : []));
                
                return $q(function (resolve, reject) {
                    service.$adapter.find(data).then(function (result) {
                        try {
                            service.clear();
                            service.$result = [];
                            
                            result.forEach(function (v, k) {
                                service.$retrieveInstance(v.getPrimaryKey(), v, false);
                            });
                            
                            service.$total = service.$adapter.getTotal();
                            resolve(result);
                        } catch (error) {
                            reject(error);
                        }
                    }, function (error) {
                        reject(error);
                    });
                });
            },
            getCollection: function () {
                return this.$result;
            },
            getTotal: function () {
                return this.$total;
            },
            remove: function (data) {
                try {
                    var deferred = $q.defer(),
                        scope = this,
                        objs = [];

                    if (angular.isArray(data)) {
                        for (var k in data) {
                            if (angular.isDefined(data[k].getTypeName)) {
                                delete this.$pool[angular.toJson(data[k].getPrimaryKey())];
                                objs.push(data[k]);
                            }
                        }
                    } else if (angular.isObject(data)) {
                        if (angular.isDefined(data[k].getTypeName)) {
                            delete this.$pool[angular.toJson(data.getPrimaryKey())];
                            objs.push(data[k]);
                        }
                    }

                    if (objs.length > 0) {
                        this.$adapter.remove(objs).then(function () {
                            deferred.resolve(objs);
                        }, function (e) {
                            deferred.reject(e);
                        }, function (m) {
                            deferred.notify(m);
                        });
                    } else {
                        new Error("Nenhum registro foi passado para remoção");
                    }
                } catch (e) {
                    deferred.reject(e);
                }

                return deferred.promise;
            },
            save: function (object) {
                try {
                    var promises = [],
                        service = this;

                    if (angular.isDefined(object)) {
                        if (angular.isDefined(object.getTypeName)) {
                            return $q(function (resolve, reject) {
                                var deferred = {},
                                    key = '';                                
                                
                                if (!service.$search(object.getPrimaryKey())) {
                                    key = angular.toJson(object.getPrimaryKey());
                                    deferred = service.$adapter.insert(object);
                                } else {
                                    deferred = service.$adapter.update(object);
                                }

                                deferred.then(function (object) {
                                    delete service.$pool[key];
                                    resolve(service.$retrieveInstance(object.getPrimaryKey(), object, false));
                                }, function (e) {
                                    reject(e);
                                });
                            });
                        } else {
                            throw new Error('Objecto passado por parâmetro não é um Modelo.');
                        }
                    } else {
                        for (var i in service.$pool) {
                            if (service.$pool[i].toSave) {
                                promises.push($q(function (resolve, reject) {
                                    var deferred = {};
                                    
                                    if (!service.$search(service.$pool[i].object.getPrimaryKey())){
                                        deferred = service.$adapter.insert(service.$pool[i].object)
                                    } else {
                                        deferred = service.$adapter.update(service.$pool[i].object)
                                    }
                                    
                                    deferred.then(function (object) {
                                        delete service.$pool[i];
                                        resolve(service.$retrieveInstance(object.getPrimaryKey(), object), false);
                                    }, function (e) {
                                        reject(e);
                                    });
                                }));
                            }
                        }

                        return $q.all(promises);
                    }
                } catch (e) {
                    return $q(function (resolve, reject) {
                        reject(e);
                    });
                }
            }
        };

        return GenericManager;
    }]);

