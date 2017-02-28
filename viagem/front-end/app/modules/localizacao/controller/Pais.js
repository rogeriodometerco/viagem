
"use strict";

angular.module("Localizacao")
  .controller('Localizacao.Controller.Pais', ['$scope', 'Localizacao.Service.Pais', 
    function($scope, Service) {
        
      $scope.templates = {
          list:   'localizacao/view/pais.list.html',
          detail: 'localizacao/view/pais.edit.html'
      };
      $scope.manager = new Service;
      $scope.multiSelect = true;
      $scope.columns = [
          { field: "id", displayName: "Id", width: 50},
          { field: "nome", displayName: "Nome", minWidth: 200}
      ];
        
  }]);