
"use strict";

angular.module("Geral")
  .factory('Geral.Service.Conta', ['Generic.Service.Generic', 'Geral.Model.Conta', function(Service, Conta) {
      
    var Manager = Service.extend('Geral.Service.Conta', {
      $model: Conta
    });

    return Manager;
  
  }]);