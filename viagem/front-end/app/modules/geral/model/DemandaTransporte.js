
"use strict";

angular.module('Geral')
  .factory('Geral.Model.DemandaTransporte', ['Generic.Model.Generic', function(GenericModel){
          
    var Class = GenericModel.extend("Geral.Model.DemandaTransporte", {
      fields: [
        {name: "id", field: "id", primaryKey: true},
        {
          name: "origem",
          field: "origem",
          association: {
            model: 'Geral.Model.Estabelecimento'
          }
        },
        {
          name: "destino",
          field: "destino",
          association: {
            model: 'Geral.Model.Estabelecimento'
          }
        },
        {
          name: "produto",
          field: "produto",
          association: {
            model: 'Geral.Model.Produto'
          }
        },
        {name: "quantidade", field: "quantidade"},
        {
          name: "unidadeQuantidade",
          field: "unidadeQuantidade",
          association: {
            model: 'Geral.Model.UnidadeQuantidade'
          }
        },
        {name: "status", field: "status"},
        {
          name: "tomador",
          field: "tomador",
          association: {
            model: 'Geral.Model.Conta'
          }
        },
        {
          name: 'transportadores',
          association: {
            manager: 'Geral.Service.TransportadorDemandaAutorizado',
            getterName: 'transportadores',
            key: [{
              foreing: 'demanda.id',
              field: 'id'
            }]
          }
        }
      ],
      resource: {
        baseUrl: 'demandaTransporte'
      },
      construct: function(){
        this.$.construct.call(this);
      }
    });
    
    return Class;
          
  }]);
	
