"use script";

angular.module("Generic")
  .provider("Generic.HttpDataAdapter", function () {
    var baseUrl = '', paramsDefault = {};

    return {
      setBaseUrl: function (url) {
        baseUrl = url;
      },
      setParamsDefault: function (params) {
        paramsDefault = params;
      },
      getParamsDefault: function () {
        return paramsDefault;
      },
      $get: ['$dataBase', '$q', '$http', '$injector', function ($database, $q, $http, $injector) {
        function HttpDataAdapter(resource) {
          var resourceName, total = 0;

          if (!angular.isDefined(resource)) {
            resourceName = '';
          } else if (angular.isString(resource)) {
            resourceName = resource;
          } else if (angular.isDefined(resource.prototype.getTypeName)) {
            resourceName = resource.prototype.resource.baseUrl;
          }

          function convertDateString(data) {

            $.each(data, function (k, v) {
              if (angular.isDate(v)) {
                data[k] = moment(v).format("DD/MM/YYYY hh:mm:ss");
              } else if (angular.isObject(v)) {
                data[k] = convertDateString(v);
              } else
                data[k] = v;
            });

            return data;

          }

          function convertDate(data, resource) {

            var manager, model;

            if (angular.isDefined(resource.prototype.fields)) {
              $.each(data, function (k, v) {
                if (angular.isDefined(resource.prototype.fieldsName[k])) {
                  if (resource.prototype.fieldsName[k].type === 'datetime') {
                    data[k] = moment(v, "DD/MM/YYYY HH:mm:ss").toDate();
                  } else if (angular.isDefined(resource.prototype.fieldsName[k].association)) {
                    if (angular.isDefined(resource.prototype.fieldsName[k].association.manager) && angular.isArray(v)) {
                      if (v.length > 0) {
                        manager = $injector.get(resource.prototype.fieldsName[k].association.manager);
                        model = manager.prototype.$model;
                        $.each(v, function (k2, v2) {
                          v[k2] = convertDate(v2, model);
                        });
                      }
                    } else if (angular.isDefined(resource.prototype.fieldsName[k].association.model)) {
                      model = $injector.get(resource.prototype.fieldsName[k].association.model);
                      v = convertDate(v, model);
                    }
                    data[k] = v;
                  } else
                    data[k] = v;
                } else {
                  throw new Error('Campo ' + k + ' não existe no modelo ' + resource.prototype.getTypeName());
                }
              });
            }

            return data;

          }

          this.getParamsDefault = function () {
            return paramsDefault;
          };

          this.responseInterceptor = function (data) {
            function convertModel(value) {
              if (angular.isDefined(resource) && !angular.isString(resource)) {
                if (!angular.isDefined(value.getTypeName)) {
                  var model = new resource;
                  value = convertDate(value, resource);
                  model.set(value);
                  return model;
                }
              }

              return value;
            };

            if (angular.isArray(data)) {
              for (var i in data) {
                data[i] = convertModel(data[i]);
              }
            } else if (angular.isObject(data)) {
              data = convertModel(data);
            }

            return data;
          };

          this.request = function (data, interceptor) {
            return $q(function (resolve, reject) {

              var params;

              if(angular.isDefined(data.page) && angular.isDefined(data.limit)){
                params = {
                  p: data.page,
                  t: data.limit
                };
              }

              angular.extend(data, paramsDefault);
              data = convertDateString(data);

              $http({
                method: data.method,
                url: baseUrl+(data.resource ? data.resource : resourceName),
                params: params ? params : null,
                data: data.data ? data.data : null
              }).then(function (result) {
                try {
                  return resolve(angular.isDefined(interceptor) ? interceptor(result.data) : result.data);
                } catch (e) {
                  reject(e);
                }
              }, function (error) {
                if (angular.isObject(error.data.erro)) {
                  if (angular.isDefined(error.data.erro.mensagem)) {
                    reject(new Error(error.data.erro.mensagem))
                    return;
                  }
                }

                reject(new Error('Não foi possível realizar a requisição, tente mais tarde.'));
              });
            });
          };

          this.findById = function (id) {
            var responseInterceptor = this.responseInterceptor,
              obj = resource ? new resource : '',
              data = {
                resource: resourceName,
                action: 'obter',
                data: {}
              };

            data.data[obj ? obj.$primaryKey[0] : 'id'] = id;

            if (angular.isDefined(resource.prototype)) {
              if (angular.isDefined(resource.prototype.getTypeName)) {
                data.data = resource.prototype.$convertObjectToFields(data.data);
              }
            }

            return this.request(data, function (result) {
              return responseInterceptor(result);
            });
          };

          this.find = function (data) {
            var responseInterceptor = this.responseInterceptor;

              data.method = 'GET';
            return this.request(data, function (result) {
              if (angular.isDefined(result.count)) {
                total = result.count;
              }

              var list = responseInterceptor(total > 0 ? result.lista : []);

              return list;
            });
          };

          this.getTotal = function () {
            return total;
          };

          this.insert = function (object) {

            var responseInterceptor = this.responseInterceptor;

            return this.request({
              method: 'POST',
              data: object.convertObjectToFields()
            }, function (result) {
              object.set(result);
              return responseInterceptor(result);
            });
          };

          this.update = function (object) {
            return this.insert(object);
          };

          this.remove = function (data) {
            var responseInterceptor = this.responseInterceptor,
              me = this;

            return $q(function (resolve, reject) {
              try {
                var count = 0,
                  total = 0,
                  list = [];

                if (Array.isArray(data)) {
                  total = data.length;

                  for (var i in data) {
                    list.push(me.request({
                      method: 'DELETE',
                      resource: resourceName+'/'+data[i].$convertObjectToFields(data[i].getPrimaryKey())[data[i].$primaryKey]
                    }));
                  }

                  $q.all(list).then(function (result) {
                    resolve(result);
                  }, function (error) {
                    reject(error);
                  });
                } else {
                  me.request({
                    method: 'DELETE',
                    resource: resourceName+'/'+data.$convertObjectToFields(data.getPrimaryKey())[data.$primaryKey]
                  }).then(function (result) {
                    resolve(result);
                  }, function (error) {
                    reject(error);
                  });
                }
              } catch (error) {
                reject(error);
              }
            });
          };
        };

        HttpDataAdapter.setParamsDefault = function (params) {
          paramsDefault = params;
        };

        HttpDataAdapter.getParamsDefault = function () {
          return paramsDefault;
        };

        return HttpDataAdapter;
      }]
    };
  });