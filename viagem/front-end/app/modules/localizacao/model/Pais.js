
"use strict";

angular.module('Localizacao')
  .factory('Localizacao.Model.Pais', ['Generic.Model.Generic', function(GenericModel){
          
    var Class = GenericModel.extend("Localizacao.Model.Pais", {
      fields: [
        {name: "id", field: "cd_pais", primaryKey: true},
        {name: "nome", field: "nm_pais"},
        {name: "nomeAbreviado", field: "nm_abreviado"}
      ],
      resource: {
        baseUrl: 'localizacao.pais',
        dataDefault: {
          resource: "pais"
        }
      },
      construct: function(){
        this.$.construct.call(this);
      }
    });
    
    return Class;
          
  }]);
	
