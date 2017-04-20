
"use strict";

angular.module("Geral")
  .controller('Geral.Controller.TipoVeiculo', ['$scope', 'Geral.Service.TipoVeiculo',
    function($scope, Service) {
        
      $scope.templates = {
        list:   'geral/view/tipo-veiculo.list.html',
        detail: 'geral/view/tipo-veiculo.edit.html'
      };
      $scope.manager = new Service;
      $scope.multiSelect = true;
      $scope.columns = [
        { field: "id", displayName: "Id", width: 50},
        { field: "nome", displayName: "Nome", minWidth: 200}
      ];
        
  }]);