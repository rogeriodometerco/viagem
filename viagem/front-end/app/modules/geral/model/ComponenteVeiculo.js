
"use strict";

angular.module('Geral')
  .factory('Geral.Model.ComponenteVeiculo', ['Generic.Model.Generic', function(GenericModel){
          
    var Class = GenericModel.extend("Geral.Model.ComponenteVeiculo", {
      fields: [
        {name: "id", field: "id", primaryKey: true},
        {name: "placa", field: "placa"},
        {name: "quantidadeEixos", field: "quantidadeEixos"},
        {name: "posicaoNoVeiculo", field: "posicaoNoVeiculo"},
        {
          name: "tipoCarroceria",
          field: "tipoCarroceria",
          association: {
            model: 'Geral.Model.TipoCarroceria'
          }
        },
        {
          name: "veiculo",
          field: "veiculo",
          association: {
            model: 'Geral.Model.Veiculo'
          }
        }
      ],
      resource: {
        baseUrl: 'componenteVeiculo'
      },
      construct: function(){
        this.$.construct.call(this);
      }
    });
    
    return Class;
          
  }]);
	
