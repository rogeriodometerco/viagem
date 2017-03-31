
"use strict";

angular.module("Geral")
  .factory('Geral.Service.Usuario', ['Generic.Service.Generic', 'Geral.Model.Usuario', function(Service, Usuario) {
      
    var Manager = Service.extend('Geral.Service.Usuario', {
      $model: Usuario
    });

    return Manager;
  
  }]);