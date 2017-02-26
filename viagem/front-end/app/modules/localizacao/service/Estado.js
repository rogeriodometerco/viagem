
"use strict";

angular.module("Localizacao")
  .factory('Localizacao.Service.Estado', ['Generic.Service.Generic', 'Localizacao.Model.Estado', function(Service, Estado) {
      
    var Manager = Service.extend('Localizacao.Service.Estado', {
      $model: Estado
    });

    return Manager;
  
  }]);