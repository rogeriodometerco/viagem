
"use strict";

angular.module("Geral")
  .controller('Geral.Controller.Veiculo', ['$scope', 'Geral.Service.Veiculo',
    function($scope, Service) {
        
      $scope.templates = {
        list:   'geral/view/veiculo.list.html',
        detail: 'geral/view/veiculo.edit.html'
      };
      $scope.manager = new Service;
      $scope.multiSelect = true;
      $scope.columns = [
        { field: "id", displayName: "Id", width: 50},
        { field: "placa", displayName: "Placa", minWidth: 200}
      ];
        
  }]);