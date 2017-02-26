
"use strict";

angular.module("Localizacao")
  .controller('Localizacao.Controller.Cidade', ['$scope', 'Localizacao.Service.Cidade', 
    function($scope, Service) {
        
      $scope.templates = {
          list:   'localizacao/view/cidade.list.html',
          detail: 'localizacao/view/cidade.edit.html'
      };
      $scope.manager = new Service;
      $scope.multiSelect = true;
      $scope.columns = [
          { field: "id", displayName: "Id", width: 50},
          { field: "nome", displayName: "Nome", minWidth: 200},
          { field: "estado.$nome", displayName: "Estado", minWidth: 200}
      ];
        
  }]);