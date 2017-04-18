
"use strict";

angular.module('Geral')
  .factory('Geral.Model.Produto', ['Generic.Model.Generic', function(GenericModel){
          
    var Class = GenericModel.extend("Geral.Model.Produto", {
      fields: [
        {name: "id", field: "id", primaryKey: true},
        {name: "nome", field: "nome"}
      ],
      resource: {
        baseUrl: 'produto'
      },
      construct: function(){
        this.$.construct.call(this);
      }
    });
    
    return Class;
          
  }]);
	
