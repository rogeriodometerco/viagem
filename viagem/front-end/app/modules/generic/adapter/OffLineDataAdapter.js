"use script";

angular.module("Generic")
        .factory("Generic.OffLineDataAdapter", ['$dataBase', '$q', function ($database, $q) {
                return function (collection) {

                    var collectionName, total = 0;

                    if (angular.isString(collection))
                        collectionName = collection;
                    else if (angular.isDefined(collection.prototype.getTypeName))
                        collectionName = collection.prototype.resource.dataDefault.resource;

                    function navigateField(field, data) {
                        var fields = field.split(".");
                        field = data;
                        for (var i in fields) {
                            field = field[fields[i]];
                        }
                        return field;
                    }
                    function convertTypeValue(field, value) {
                        if (angular.isDefined(collection.prototype)) {
                            if (angular.isDefined(collection.prototype.fields[field])) {
                                if (collection.prototype.fields[field].type === 'datetime') {
                                    return new Date(value);
                                }
                            }
                        }
                        return value;
                    }
                    function validateFilter(filters, data) {
                        for (var i in filters) {
                            var filter = filters[i]
                            valid = true,
                                    str = '';
                            if (filter.value) {
                                if (filter.condition === '=' || !angular.isDefined(filter.condition)) {
                                    valid = navigateField(filter.field, data) == convertTypeValue(filter.field, filter.value);
                                } else if (filter.condition === '=%') {
                                    valid = navigateField(filter.field, data).toUpperCase().indexOf(String(filter.value).toUpperCase()) === 0;
                                } else if (filter.condition === '%=') {
                                    str = navigateField(filter.field, data);
                                    valid = navigateField(filter.field, data).toUpperCase().indexOf(String(filter.value).toUpperCase()) === str.length - String(filter.value).length - 1;
                                } else if (filter.condition === '%=%') {
                                    valid = navigateField(filter.field, data).toUpperCase().indexOf(String(filter.value).toUpperCase()) >= 0;
                                } else if (filter.condition === '>') {
                                    valid = navigateField(filter.field, data) > convertTypeValue(filter.field, filter.value);
                                } else if (filter.condition === '>=') {
                                    valid = navigateField(filter.field, data) >= convertTypeValue(filter.field, filter.value);
                                } else if (filter.condition === '<') {
                                    valid = navigateField(filter.field, data) < convertTypeValue(filter.field, filter.value);
                                } else if (filter.condition === '<=') {
                                    valid = navigateField(filter.field, data) <= convertTypeValue(filter.field, filter.value);
                                }
                            }
                            if (!valid)
                                return valid;
                        }
                        return true;
                    }

                    this.responseInterceptor = function (data) {

                        function convertModel(value) {
                            if (!angular.isDefined(value.getTypeName)) {
                                var model = new collection;
                                model.set(value);
                                return model;
                            } else
                                return value;
                        }

                        if (angular.isArray(data)) {
                            for (var i in data) {
                                data[i] = convertModel(data[i]);
                            }
                        } else if (angular.isObject) {
                            data = convertModel(data);
                        }

                        return data;

                    }

                    this.findById = function (id) {

                        var responseInterceptor = this.responseInterceptor;

                        return $q(function (resolve, reject) {
                            $database.objectStore(collectionName, null, function (store) {
                                var request = store.get(parseInt(id));

                                request.onsuccess = function (event) {
                                    return resolve(responseInterceptor(event.target.result));
                                };

                                request.onerror = function (event) {
                                    return reject(event.target.error);
                                };
                            });
                        });
                    };

                    this.find = function (data) {

                        var responseInterceptor = this.responseInterceptor;

                        return $q(function (resolve, reject) {

                            $database.objectStore(collectionName, null, function (store, transaction) {

                                try {

                                    var index = null,
                                            cursorRequest = null,
                                            resultSet = [],
                                            count = 0;

                                    try {
                                        index = store.index(data.sort);
                                    } catch (e) {
                                        index = store;
                                    }

                                    cursorRequest = index.openCursor(
                                            null,
                                            data.dir !== "" && angular.isDefined(data.dir) ? (data.dir === "asc" ? "next" : "prev") : "next"
                                            );
                                    transaction.oncomplete = function () {
                                        total = count;
                                        return resolve(responseInterceptor(resultSet));
                                    };

                                    cursorRequest.onsuccess = function (event) {
                                        var cursor = event.target.result;

                                        if (cursor) {

                                            if (validateFilter(data.data, cursor.value)) {

                                                if (angular.isDefined(data.start) && angular.isDefined(data.limit)) {
                                                    if (!(data.start <= count && data.start + data.limit > count)) {
                                                        cursor['continue']();
                                                        count++;
                                                        return;
                                                    }
                                                }

                                            } else {
                                                cursor['continue']();
                                                return;
                                            }

                                            //var model = new collection;
                                            count++;
                                            //model.set(cursor.value);
                                            resultSet.push(cursor.value);
                                            cursor['continue']();

                                        }


                                    };

                                    cursorRequest.onerror = function (event) {
                                        return reject(event.target.error);
                                    };

                                } catch (e) {
                                    reject(e);
                                }

                            });

                        });

                    };

                    this.getTotal = function () {
                        return total;
                    };

                    this.insert = function (object) {

                        var responseInterceptor = this.responseInterceptor;

                        return $q(function (resolve, reject) {

                            $database.objectStore(collectionName, null, function (store, transaction) {

                                try {

                                    var data = object.convertObjectToFields();
                                    var request = store.add(data);

                                    request.onsuccess = function (event) {
                                        object.set({
                                            id: event.target.result
                                        });
                                        return resolve(responseInterceptor(object));
                                    };

                                    request.onerror = function (event) {
                                        return reject(event.target.error);
                                    };

                                } catch (error) {
                                    reject(error);
                                }

                            });
                        });
                    };

                    this.update = function (object) {

                        var responseInterceptor = this.responseInterceptor;

                        return $q(function (resolve, reject) {

                            $database.objectStore(collectionName, null, function (store, transaction) {

                                try {

                                    var data = object.convertObjectToFields();
                                    var request = store.put(data);

                                    request.onsuccess = function (event) {
                                        return resolve(responseInterceptor(object));
                                    };

                                    request.onerror = function (event) {
                                        return reject(event.target.error);
                                    };

                                } catch (error) {
                                    reject(error);
                                }

                            });
                        });
                    };
                    ;

                    this.remove = function (data) {

                        var responseInterceptor = this.responseInterceptor;

                        return $q(function (resolve, reject) {
                            $database.objectStore(collectionName, null, function (store, transaction) {

                                try {

                                    var count = 0,
                                            total = 0,
                                            request = {};

                                    if (Array.isArray(data)) {
                                        total = data.length;
                                        for (var i in data) {
                                            request = store['delete'](data[i].get('id'));
                                            request.onsuccess = function (event) {
                                                count++;
                                                if (count === total)
                                                    return resolve(responseInterceptor(data));
                                            };
                                            request.onerror = function (event) {
                                                return reject(event.target.error);
                                            };
                                        }
                                    } else {
                                        request = store['delete'](data.get('id'));
                                        request.onsuccess = function (event) {
                                            return resolve(responseInterceptor(data));
                                        };
                                        request.onerror = function (event) {
                                            return reject(event.target.error);
                                        };
                                    }

                                } catch (error) {
                                    reject(error);
                                }

                            });
                        });
                    };
                };
            }])