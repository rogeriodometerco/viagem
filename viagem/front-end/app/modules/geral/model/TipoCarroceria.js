
"use strict";

angular.module('Geral')
  .factory('Geral.Model.TipoCarroceria', ['Generic.Model.Generic', function(GenericModel){
          
    var Class = GenericModel.extend("Geral.Model.TipoCarroceria", {
      fields: [
        {name: "id", field: "id", primaryKey: true},
        {name: "nome", field: "nome"}
      ],
      resource: {
        baseUrl: 'tipoCarroceria'
      },
      construct: function(){
        this.$.construct.call(this);
      }
    });
    
    return Class;
          
  }]);
	
