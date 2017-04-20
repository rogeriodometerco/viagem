
"use strict";

angular.module("Geral")
  .factory('Geral.Service.TipoCarroceria', ['Generic.Service.Generic', 'Geral.Model.TipoCarroceria', function(Service, TipoCarroceria) {
      
    var Manager = Service.extend('Geral.Service.TipoCarroceria', {
      $model: TipoCarroceria
    });

    return Manager;
  
  }]);