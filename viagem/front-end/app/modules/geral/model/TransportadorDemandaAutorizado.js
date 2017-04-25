
"use strict";

angular.module('Geral')
  .factory('Geral.Model.TransportadorDemandaAutorizado', ['Generic.Model.Generic', function(GenericModel){
          
    var Class = GenericModel.extend("Geral.Model.TransportadorDemandaAutorizado", {
      fields: [
        {name: "id", field: "id", primaryKey: true},
        {
          name: "demanda",
          field: "demanda",
          association: {
            model: 'Geral.Model.DemandaTransporte'
          }
        },
        {
          name: "transportador",
          field: "transportador",
          association: {
            model: 'Geral.Model.Conta'
          }
        },
        {name: "ativo", field: "ativo", type: "boolean"}
      ],
      resource: {
        baseUrl: 'transportadorDemandaAutorizado'
      },
      construct: function(){
        this.$.construct.call(this);
      }
    });
    
    return Class;
          
  }]);
	
