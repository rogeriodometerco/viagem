
"use strict";

angular.module("Localizacao")
  .factory('Localizacao.Service.Cidade', ['Generic.Service.Generic', 'Localizacao.Model.Cidade', function(Service, Cidade) {
      
    var Manager = Service.extend('Localizacao.Service.Cidade', {
      $model: Cidade
    });

    return Manager;
  
  }]);