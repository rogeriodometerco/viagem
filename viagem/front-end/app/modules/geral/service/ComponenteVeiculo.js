
"use strict";

angular.module("Geral")
  .factory('Geral.Service.ComponenteVeiculo', ['Generic.Service.Generic', 'Geral.Model.ComponenteVeiculo', function(Service, ComponenteVeiculo) {
      
    var Manager = Service.extend('Geral.Service.ComponenteVeiculo', {
      $model: ComponenteVeiculo
    });

    return Manager;
  
  }]);