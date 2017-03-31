
"use strict";

angular.module("Geral")
  .controller('Geral.Controller.Usuario', ['$scope', 'Geral.Service.Usuario',
    function($scope, Service) {
        
      $scope.templates = {
        list:   'geral/view/usuario.list.html',
        detail: 'geral/view/usuario.edit.html'
      };
      $scope.manager = new Service;
      $scope.multiSelect = true;
      $scope.columns = [
        { field: "id", displayName: "Id", width: 50},
        { field: "nome", displayName: "Nome", minWidth: 200},
        { field: "login", displayName: "Login", minWidth: 200}
      ];
        
  }]);