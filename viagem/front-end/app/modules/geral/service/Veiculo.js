
"use strict";

angular.module("Geral")
  .factory('Geral.Service.Veiculo', ['Generic.Service.Generic', 'Geral.Model.Veiculo', function(Service, Veiculo) {
      
    var Manager = Service.extend('Geral.Service.Veiculo', {
      $model: Veiculo
    });

    return Manager;
  
  }]);