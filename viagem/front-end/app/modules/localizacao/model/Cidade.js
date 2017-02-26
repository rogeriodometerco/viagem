
"use strict";

angular.module('Localizacao')
  .factory('Localizacao.Model.Cidade', ['Generic.Model.Generic', function(GenericModel){
          
    var Class = GenericModel.extend("Localizacao.Model.Cidade", {
      fields: [
        {name: "id", field: "id", primaryKey: true},
        {name: "nome", field: "nome"},
        {
          name: "estado",
          field: "uf",
          association: {
              model: 'Localizacao.Model.Estado'
          }
        }
      ],
      resource: {
        baseUrl: 'municipio'
      },
      construct: function(){
        this.$.construct.call(this);
      }
    });
    
    return Class;
          
  }]);
	
