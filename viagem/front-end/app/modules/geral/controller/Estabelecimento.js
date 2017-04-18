
"use strict";

angular.module("Geral")
  .controller('Geral.Controller.Estabelecimento', ['$scope', '$http', 'Geral.Service.Estabelecimento', 'EVENTS',
    function($scope, $http, Service, EVENTS) {
        
      $scope.templates = {
        list:   'geral/view/estabelecimento.list.html',
        detail: 'geral/view/estabelecimento.edit.html'
      };
      $scope.manager = new Service;
      $scope.multiSelect = true;
      $scope.columns = [
        { field: "id", displayName: "Id", width: 50},
        { field: "nome", displayName: "Nome", minWidth: 200},
        { field: "endereco", displayName: "Cidade", minWidth: 200, renderer: function(value){
          return value.get('cidade').get('nome');
        }}
      ];

      $scope.detailScope = function(scope){

        if(!angular.isDefined(scope.modelEdit.id))
          scope.modelEdit = {
            endereco: {
              cidade: {}
            }
          };

        scope.$watch(function(scope){
          return scope.cep;
          return scope.modelEdit.endereco.cep;
        }, function(n, o){
          if(n !== o){

            n = n.replace(/[-\.]/g, '');
            if(n.length === 8){
              scope.$emit(EVENTS.wait);
              $http.get('https://viacep.com.br/ws/'+n+'/json').then(function(data){
                scope.$emit(EVENTS.ready);
                if(!angular.isDefined(data.data.erro)){
                  scope.modelEdit.endereco.logradouro = data.data.logradouro;
                  scope.modelEdit.endereco.bairro = data.data.bairro;
                  /*scope.municipio.manager.find({data: [{
                    field: 'codigoIBGE',
                    condition: '=',
                    value: data.data.ibge
                  }]}).then(function(result){
                    if(result.length > 0)
                      $scope.municipio.selectModel(result[0]);
                    else{
                      var cidade = $scope.municipio.manager.create();
                      cidade.set({
                        nome: data.data.localidade,
                        estado: {
                          id: data.data.uf
                        }
                      });
                      $scope.municipio.selectModel(cidade);
                    }
                  }, function(error){
                    var cidade = $scope.municipio.manager.create();
                    cidade.set({
                      nome: data.data.localidade,
                      estado: {
                        id: data.data.uf
                      }
                    });
                    $scope.municipio.selectModel(cidade);
                  });*/
                  $('#numero').focus();
                }else{
                  scope.modelEdit.endereco.logradouro  = '';
                  scope.modelEdit.endereco.bairro      = '';
                }
              }, function(error){
                scope.$emit(EVENTS.ready);
              });
            }

          }
        });

      }
        
  }]);