
"use strict";

angular.module("Geral")
  .controller('Geral.Controller.Produto', ['$scope', 'Geral.Service.Produto',
    function($scope, Service) {
        
      $scope.templates = {
        list:   'geral/view/produto.list.html',
        detail: 'geral/view/produto.edit.html'
      };
      $scope.manager = new Service;
      $scope.multiSelect = true;
      $scope.columns = [
        { field: "id", displayName: "Id", width: 50},
        { field: "nome", displayName: "Nome", minWidth: 200}
      ];
        
  }]);