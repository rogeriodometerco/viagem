
"use strict";

angular.module('Localizacao')
  .factory('Localizacao.Model.Cidade', ['Generic.Model.Generic', function(GenericModel){
          
    var Class = GenericModel.extend("Localizacao.Model.Cidade", {
      fields: [
        {name: "id", field: "cd_municipio", primaryKey: true},
        {name: "nome", field: "nm_municipio"},
        {name: "codigoIBGE", field: "cd_ibge"},
        {name: "sefaz", field: "cd_sefaz"},
        {name: "codigoEstado", field: "cd_uf"},
        {
          name: "pais",
          association: {
              model: 'Localizacao.Model.Pais'
          }
        },
        {
          name: "estado",
          field: "uf",
          association: {
              model: 'Localizacao.Model.Estado'
          }
        }
      ],
      resource: {
        baseUrl: 'localizacao.cidade',
        dataDefault: {
          resource: "municipio"
        }
      },
      construct: function(){
        this.$.construct.call(this);
      }
    });
    
    return Class;
          
  }]);
	
