
"use strict";

angular.module("Geral")
  .controller('Geral.Controller.DemandaTransporte', ['$scope', 'Geral.Service.DemandaTransporte', '$uibModal',
    function($scope, Service, $uibModal) {
        
      $scope.templates = {
        list:   'geral/view/demanda-transporte.list.html',
        detail: 'geral/view/demanda-transporte.edit.html'
      };
      $scope.manager = new Service;
      $scope.multiSelect = true;
      $scope.columns = [
        { field: "id", displayName: "Id", width: 50},
        { field: "origem.$nome", displayName: "Origem", minWidth: 200},
        { field: "destino.$nome", displayName: "Destino", minWidth: 200},
        { field: "produto.$nome", displayName: "Produto", minWidth: 200},
        { field: "quantidade", displayName: "Qtd.", minWidth: 150, renderer: function(value, entity){
          return value + ' ' + entity.get('unidadeQuantidade').get('abreviacao');
        }}
      ];

      $scope.detailScope = function(scope){

        var scopeModal = scope.$new();

        if(!angular.isDefined(scope.modelEdit.id))
          scope.modelEdit = $scope.manager.create().convertToObject();

        scopeModal.multiSelect = true;
        scopeModal.selections = [];

        scope.openTransportadorModal = function() {
          return $uibModal.open({
            templateUrl: "views/geral/view/conta.list.html",
            windowTemplateUrl: 'windowSelect.html',
            controller: 'Geral.Controller.Conta',
            scope: scopeModal,
            size: 'md'
          });
        };

        scope.addTransportador = function () {
          var modalInstance = scope.openTransportadorModal();

          scopeModal.select = function(){
            modalInstance.close(scopeModal.selections);
          };
          scopeModal.cancel = function(){
            modalInstance.dismiss("cancel");
          };

          modalInstance.result.then(function(transportadores){
            $.each(transportadores, function(k, v){
              scope.modelEdit.transportadores.push({transportador: v.convertToObject()});
            });
          }, function(e){});
        };
      };

  }]);