
"use strict";

angular.module('Geral')
  .factory('Geral.Model.UnidadeQuantidade', ['Generic.Model.Generic', function(GenericModel){
          
    var Class = GenericModel.extend("Geral.Model.UnidadeQuantidade", {
      fields: [
        {name: "id", field: "id", primaryKey: true},
        {name: "abreviacao", field: "abreviacao"},
        {name: "nome", field: "nome"}
      ],
      resource: {
        baseUrl: 'unidadeQuantidade'
      },
      construct: function(){
        this.$.construct.call(this);
      }
    });
    
    return Class;
          
  }]);
	
