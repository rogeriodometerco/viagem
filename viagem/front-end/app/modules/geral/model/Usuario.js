
"use strict";

angular.module('Geral')
  .factory('Geral.Model.Usuario', ['Generic.Model.Generic', function(GenericModel){
          
    var Class = GenericModel.extend("Geral.Model.Usuario", {
      fields: [
        {name: "id", field: "id", primaryKey: true},
        {name: "nome", field: "nome"},
        {name: "login", field: "login"},
        {name: "senha", field: "senha"}
      ],
      resource: {
        baseUrl: 'usuario'
      },
      construct: function(){
        this.$.construct.call(this);
      }
    });
    
    return Class;
          
  }]);
	
