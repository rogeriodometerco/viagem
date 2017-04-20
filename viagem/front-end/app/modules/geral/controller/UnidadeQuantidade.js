
"use strict";

angular.module("Geral")
  .controller('Geral.Controller.UnidadeQuantidade', ['$scope', 'Geral.Service.UnidadeQuantidade',
    function($scope, Service) {
        
      $scope.templates = {
        list:   'geral/view/unidade-quantidade.list.html',
        detail: 'geral/view/unidade-quantidade.edit.html'
      };
      $scope.manager = new Service;
      $scope.multiSelect = true;
      $scope.columns = [
        { field: "id", displayName: "Id", width: 50},
        { field: "abreviacao", displayName: "Abreviatura", minWidth: 100},
        { field: "nome", displayName: "Nome", minWidth: 200}
      ];
        
  }]);