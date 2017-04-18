
"use strict";

angular.module("Geral")
  .factory('Geral.Service.UnidadeQuantidade', ['Generic.Service.Generic', 'Geral.Model.UnidadeQuantidade', function(Service, UnidadeQuantidade) {
      
    var Manager = Service.extend('Geral.Service.UnidadeQuantidade', {
      $model: UnidadeQuantidade
    });

    return Manager;
  
  }]);