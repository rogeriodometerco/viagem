
"use strict";

angular.module("Geral")
  .controller('Geral.Controller.Conta', ['$scope', 'Geral.Service.Conta', 'Generic.HttpDataAdapter',
    function($scope, Service, DataAdapter) {
        
      $scope.templates = {
          list:   'geral/view/conta.list.html',
          detail: 'geral/view/conta.edit.html'
      };
      $scope.manager = new Service;
      $scope.multiSelect = true;
      $scope.columns = [
          { field: "id", displayName: "Id", width: 50},
          { field: "nome", displayName: "Nome", minWidth: 200}
      ];

      $scope.detailScope = function(scopeD){

        scopeD.perfis = [];
        scopeD.data = {
          perfis: {}
        };
        $.each(scopeD.modelEdit.perfis, function(k, v){
          scopeD.data.perfis[v.perfil] = true;
        });

        var dataAdapter = new DataAdapter('perfil');
        dataAdapter.request({
          method: 'GET'
        }).then(function(data){
          scopeD.perfis = data;
          $.each(data, function(k, v){
            if(!angular.isDefined(scopeD.data.perfis[v]))
              scopeD.data.perfis[v] = false;
          });
        });

        scopeD.$watch(function(scopeD){
          return scopeD.data.perfis;
        }, function(n, o){
          scopeD.modelEdit.perfis = [];
          $.each(scopeD.data.perfis, function(k, v){
            if(v){
              scopeD.modelEdit.perfis.push({
                perfil: k
              })
            }
          })
        }, true);

      }
        
  }]);