
"use strict";

angular.module("Geral")
  .factory('Geral.Service.TipoVeiculo', ['Generic.Service.Generic', 'Geral.Model.TipoVeiculo', function(Service, TipoVeiculo) {
      
    var Manager = Service.extend('Geral.Service.TipoVeiculo', {
      $model: TipoVeiculo
    });

    return Manager;
  
  }]);