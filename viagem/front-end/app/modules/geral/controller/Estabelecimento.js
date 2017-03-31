
"use strict";

angular.module("Geral")
  .controller('Geral.Controller.Estabelecimento', ['$scope', 'Geral.Service.Estabelecimento',
    function($scope, Service) {
        
      $scope.templates = {
        list:   'geral/view/estabelecimento.list.html',
        detail: 'geral/view/estabelecimento.edit.html'
      };
      $scope.manager = new Service;
      $scope.multiSelect = true;
      $scope.columns = [
        { field: "id", displayName: "Id", width: 50},
        { field: "nome", displayName: "Nome", minWidth: 200},
        { field: "cidade", displayName: "Cidade", minWidth: 200, renderer: function(value){
          return value.get('nome');
        }}
      ];
        
  }]);