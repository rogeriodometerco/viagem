
"use strict";

angular.module('Localizacao')
  .factory('Localizacao.Model.Estado', ['Generic.Model.Generic', function(GenericModel){
          
    var Class = GenericModel.extend("Localizacao.Model.Estado", {
      fields: [
        {name: "id", field: "id", primaryKey: true},
        {name: "nome", field: "nome"},
        {name: "sigla", field: "abreviatura"}
      ],
      resource: {
        baseUrl: 'uf'
      },
      construct: function(){
        this.$.construct.call(this);
      }
    });
    
    return Class;
          
  }]);
	
