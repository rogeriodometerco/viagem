
"use strict";

angular.module("Localizacao")
  .factory('Localizacao.Service.Pais', ['Generic.Service.Generic', 'Localizacao.Model.Pais', function(Service, Pais) {
      
    var Manager = Service.extend('Localizacao.Service.Pais', {
      $model: Pais
    });

    return Manager;
  
  }]);