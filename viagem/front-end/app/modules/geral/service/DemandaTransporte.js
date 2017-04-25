
"use strict";

angular.module("Geral")
  .factory('Geral.Service.DemandaTransporte', ['Generic.Service.Generic', 'Geral.Model.DemandaTransporte', function(Service, DemandaTransporte) {
      
    var Manager = Service.extend('Geral.Service.DemandaTransporte', {
      $model: DemandaTransporte
    });

    return Manager;
  
  }]);