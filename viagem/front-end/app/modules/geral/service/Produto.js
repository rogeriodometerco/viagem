
"use strict";

angular.module("Geral")
  .factory('Geral.Service.Produto', ['Generic.Service.Generic', 'Geral.Model.Produto', function(Service, Produto) {
      
    var Manager = Service.extend('Geral.Service.Produto', {
      $model: Produto
    });

    return Manager;
  
  }]);