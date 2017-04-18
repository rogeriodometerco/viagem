
"use strict";

angular.module('Geral')
  .factory('Geral.Model.TipoVeiculo', ['Generic.Model.Generic', function(GenericModel){
          
    var Class = GenericModel.extend("Geral.Model.TipoVeiculo", {
      fields: [
        {name: "id", field: "id", primaryKey: true},
        {name: "nome", field: "nome"}
      ],
      resource: {
        baseUrl: 'tipoVeiculo'
      },
      construct: function(){
        this.$.construct.call(this);
      }
    });
    
    return Class;
          
  }]);
	
