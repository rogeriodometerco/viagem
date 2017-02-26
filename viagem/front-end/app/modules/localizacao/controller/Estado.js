
"use strict";

angular.module("Localizacao")
  .controller('Localizacao.Controller.Estado', ['$scope', 'Localizacao.Service.Estado', 
    function($scope, Service) {
        
      $scope.templates = {
          list:   'localizacao/view/estado.list.html',
          detail: 'localizacao/view/estado.edit.html'
      };
      $scope.manager = new Service;
      $scope.multiSelect = true;
      $scope.columns = [
          { field: "id", displayName: "Id", width: 50},
          { field: "nome", displayName: "Nome", minWidth: 200}
      ];
        
  }]);