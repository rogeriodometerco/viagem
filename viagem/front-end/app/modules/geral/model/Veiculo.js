
"use strict";

angular.module('Geral')
  .factory('Geral.Model.Veiculo', ['Generic.Model.Generic', function(GenericModel){
          
    var Class = GenericModel.extend("Geral.Model.Veiculo", {
      fields: [
        {name: "id", field: "id", primaryKey: true},
        {name: "placa", field: "placa"}
      ],
      resource: {
        baseUrl: 'veiculo'
      },
      construct: function(){
        this.$.construct.call(this);
      }
    });
    
    return Class;
          
  }]);
	
