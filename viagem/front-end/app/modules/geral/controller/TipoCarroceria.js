
"use strict";

angular.module("Geral")
  .controller('Geral.Controller.TipoCarroceria', ['$scope', 'Geral.Service.TipoCarroceria',
    function($scope, Service) {
        
      $scope.templates = {
        list:   'geral/view/tipo-carroceria.list.html',
        detail: 'geral/view/tipo-carroceria.edit.html'
      };
      $scope.manager = new Service;
      $scope.multiSelect = true;
      $scope.columns = [
        { field: "id", displayName: "Id", width: 50},
        { field: "nome", displayName: "Nome", minWidth: 200}
      ];
        
  }]);