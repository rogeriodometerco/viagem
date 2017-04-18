
"use strict";

angular.module('Geral')
  .factory('Geral.Model.Conta', ['Generic.Model.Generic', function(GenericModel){
          
    var Class = GenericModel.extend("Geral.Model.Conta", {
      fields: [
        {name: "id", field: "id", primaryKey: true},
        {name: "nome", field: "nome"},
        {name: "ativa", field: "ativa"},
        {name: "perfis", field: "perfis"}
      ],
      resource: {
        baseUrl: 'conta'
      },
      construct: function(){
        this.$.construct.call(this);
      }
    });
    
    return Class;
          
  }]);
	
