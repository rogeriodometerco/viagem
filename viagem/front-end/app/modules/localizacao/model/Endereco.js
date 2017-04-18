
"use strict";

angular.module('Localizacao')
  .factory('Localizacao.Model.Endereco', ['Generic.Model.Generic', function(GenericModel){
          
    var Class = GenericModel.extend("Localizacao.Model.Endereco", {
      fields: [
        {name: "logradouro", field: "logradouro"},
        {name: "bairro", field: "bairro"},
        {name: "latitude", field: "latitude"},
        {name: "longitude", field: "longitude"},
        {name: "cep", field: "cep"},
        {
          name: "cidade",
          field: "municipio",
          association: {
              model: 'Localizacao.Model.Cidade'
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
	
