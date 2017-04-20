
"use strict";

angular.module('Geral')
  .factory('Geral.Model.Estabelecimento', ['Generic.Model.Generic', function(GenericModel){
          
    var Class = GenericModel.extend("Geral.Model.Estabelecimento", {
      fields: [
        {name: "id", field: "id", primaryKey: true},
        {name: "nome", field: "nome"},
        {
          name: "endereco",
          field: "endereco",
          association: {
              model: 'Localizacao.Model.Endereco'
          }
        }
      ],
      resource: {
        baseUrl: 'estabelecimento'
      },
      construct: function(){
        this.$.construct.call(this);
      }
    });
    
    return Class;
          
  }]);
	
