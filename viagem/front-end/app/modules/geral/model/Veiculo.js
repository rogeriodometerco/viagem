
"use strict";

angular.module('Geral')
  .factory('Geral.Model.Veiculo', ['Generic.Model.Generic', function(GenericModel){
          
    var Class = GenericModel.extend("Geral.Model.Veiculo", {
      fields: [
        {name: "id", field: "id", primaryKey: true},
        {
          name: "tipo",
          field: "tipo",
          association: {
            model: 'Geral.Model.TipoVeiculo'
          }
        },
        {
          name: 'componentes',
          association: {
            manager: 'Geral.Service.ComponenteVeiculo',
            getterName: 'componentes',
            key: [{
              foreing: 'veiculo.id',
              field: 'id'
            }]
          }
        }
      ],
      resource: {
        baseUrl: 'veiculo'
      },
      construct: function(){
        this.$.construct.call(this);
      }
    });
    
    return Class;
          
  }]);
	
