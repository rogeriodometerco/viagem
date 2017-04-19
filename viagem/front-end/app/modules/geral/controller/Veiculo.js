
"use strict";

angular.module("Geral")
  .controller('Geral.Controller.Veiculo', ['$scope', 'Geral.Service.Veiculo', '$uibModal',
    function($scope, Service, $uibModal) {
        
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

      $scope.detailScope = function(scope){

        scope.openComponenteModal = function(componente) {
          return $uibModal.open({
            templateUrl: 'views/geral/view/componente-veiculo.edit.html',
            controller: ['$scope', '$uibModalInstance', '$timeout', '$validator', 'componente', 'veiculo', function($scope, $uibModalInstance, $timeout, $validator, componente, veiculo){

              $scope.modelEdit = componente || {};
              $scope.veiculo = veiculo;

              $timeout(function(){
                if($scope.modelEdit === componente)
                  $scope.title = $scope.editTitle;
                else
                  $scope.title = $scope.addTitle;
              }, 100);

              $scope.save = function(){
                return $validator.validate($scope).success(function () {
                  if(angular.isDefined(veiculo) && !angular.isDefined($scope.modelEdit.veiculo)){
                    $scope.modelEdit.veiculo = angular.copy(veiculo);
                    $scope.modelEdit.veiculo.componentes = [];
                  }
                  $uibModalInstance.close($scope.modelEdit);
                }).error(function () {

                }).then(function () {

                });
              };

              $scope.cancel = function () {
                $uibModalInstance.dismiss("cancel");
              };

            }],
            size: 'md',
            resolve: {
              veiculo: function() {
                return scope.modelEdit;
              },
              componente: function () {
                return componente;
              }
            }
          });
        };
        scope.addComponente = function () {
          var modalInstance = scope.openComponenteModal(null, true),
              index;

          modalInstance.result.then(function(componente){
            scope.modelEdit.componentes.push(componente);
            index = scope.modelEdit.componentes.indexOf(componente);
            componente.posicaoNoVeiculo = index+1;
            scope.modelRead.set(scope.modelEdit);
          }, function(e){});
        };
        scope.editComponente = function (componente) {
          var index = scope.modelEdit.componentes.indexOf(componente);
          var modalInstance = scope.openComponenteModal(componente);

          modalInstance.result.then(function(componente){
            scope.modelEdit.componentes[index] = componente;
            scope.modelRead.set(scope.modelEdit);
          }, function(e){
            console.log(e);
          });
        };
        scope.deleteComponente = function (componente) {
          var index = scope.modelEdit.componentes.indexOf(componente);
          if(index >= 0){
            scope.modelEdit.componentes.splice(index, 1);
          }
        };

        scope.sortableOptions = {
          update: function(e, ui) {

          },
          stop: function(e, ui) {
            $.each(scope.modelEdit.componentes, function(k, v){
              v.posicaoNoVeiculo = k+1;
            });
          }
        };

      };
        
  }]);