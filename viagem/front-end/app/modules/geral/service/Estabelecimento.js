
"use strict";

angular.module("Geral")
  .factory('Geral.Service.Estabelecimento', ['Generic.Service.Generic', 'Geral.Model.Estabelecimento', function(Service, Estabelecimento) {
      
    var Manager = Service.extend('Geral.Service.Estabelecimento', {
      $model: Estabelecimento
    });

    return Manager;
  
  }]);