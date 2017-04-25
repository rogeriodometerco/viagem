
"use strict";

angular.module("Geral")
  .factory('Geral.Service.TransportadorDemandaAutorizado', ['Generic.Service.Generic', 'Geral.Model.TransportadorDemandaAutorizado', function(Service, TransportadorDemandaAutorizado) {
      
    var Manager = Service.extend('Geral.Service.TransportadorDemandaAutorizado', {
      $model: TransportadorDemandaAutorizado
    });

    return Manager;
  
  }]);