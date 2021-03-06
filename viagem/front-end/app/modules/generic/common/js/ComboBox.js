
"use strict";

angular.module('Generic')
  .directive('comboBox', ['$uibModal', '$timeout', 'EVENTS', function($uibModal, $timeout, EVENTS){
    function reference(attr, index, arrayPath){
      if(index === arrayPath.length-2){
        return attr[arrayPath[index]];
      }else if(arrayPath.length == 1){
        return attr;
      }else{
        if(attr[arrayPath[index]] !== undefined){
          return reference(attr[arrayPath[index]], index+1, arrayPath);
        }
      }
    }

    return {
      restrict: 'E',
      templateUrl: function(el, attrs){
        return 'select.html';
      },
      link: function(scope, el, attrs){
        var hidden          = jQuery(el[0].children[0].children[1]),
          button          = jQuery(el[0].children[0].children[2]),
          modalInstance   = null,
          divModel        = [],
          modelAux        = {};

        scope.displayField = attrs.displayField;
        scope.searchField = attrs.searchField;
        scope.validator = attrs.validator;
        scope.selectButton = attrs.selectButton == "false" ? false : true;
        scope.$parent[attrs.name] = scope
        scope.autoLoad = attrs.autoLoad == "false" ? false : true;
        scope.ngChange = attrs.ngChange;
        scope.disabled = scope.$eval(attrs.ngDisabled);

        scope.setModel = function(model){
          if(attrs.ngModel !== undefined){
            modelAux = reference(scope.$parent, 0, divModel);
            if(modelAux)
              modelAux[divModel[divModel.length-1]] = model;
          }
        }
        if(attrs.ngModel !== undefined){
          divModel = attrs.ngModel.split(".");
          modelAux = reference(scope.$parent, 0, divModel);
          if(modelAux)
            scope.select(modelAux[divModel[divModel.length-1]]);
        }

        jQuery(el[0].parentNode).append(hidden);
        $timeout(function(){
          jQuery(el[0]).find('.btn-block').append(button);
          jQuery(el[0]).find('.btn-block>.btn').removeClass('col-md-11').removeClass('col-sm-10');
          jQuery(jQuery(el[0]).find('.btn-block>.btn')[0]).addClass('col-md-8').addClass('col-xs-10').addClass('col-sm-7');
          jQuery(jQuery(el[0]).find('.btn-block>.btn')[1]).addClass('col-sm-2').addClass('hidden-xs');
        }, 100);

        button.click(function(e){
          e.preventDefault();

          if(!scope.disabled){

            try{

              var scopeModal = scope.$new();

              scopeModal.multiSelect = false;
              scopeModal.selections = [];
              modalInstance = $uibModal.open({
                templateUrl: "views/"+scope.templates.list,
                windowTemplateUrl: 'windowSelect.html',
                controller: attrs.ngController,
                scope: scopeModal,
                size: 'lg'
              });
              scopeModal.$on(EVENTS.gridready, function(e, scopeM){
                scopeM.manager.filter(scope.manager.$filter);
              });
              scopeModal.select = function(){
                modalInstance.close(scopeModal.selections);
              };
              scopeModal.cancel = function(){
                modalInstance.dismiss("cancel");
              };

              modalInstance.result.then((function(result) {
                try{
                  scope.selectModel(result[0]);
                  scope.addList(result[0]);
                }catch(e){
                  console.log(e);
                }
              }), function(result) {

              });

            }catch(e){
              console.log(e);
            }

          }

        });


      },
      controller: ['$scope', function($scope) {

        $scope.array = [];

        $timeout(function(){
          if(angular.isDefined($scope.disabled))
            $scope.disabled = true;
          else
            $scope.disabled = false;
        }, 100);

        function triggerChange(){
          if(angular.isDefined($scope.ngChange) && $scope.ngChange != ''){
            try{
              $scope.$eval($scope.ngChange);
            }catch(e){}
            try{
              eval($scope.ngChange);
            }catch(e){}
          }
        }

        function findModel(model){
          var modelF = $scope.manager.create();
          modelF.set(model);
          modelF = $scope.manager.$search(modelF.getPrimaryKey());
          if(modelF !== null)
            return modelF;
          return null;
        }

        $scope.selectModel = function($model){
          var modelF = findModel($model.convertToObject());
          if(modelF !== null)
            $model = modelF;
          $scope.select($model.convertToObject());
          if(this.$select !== undefined)
            this.$select.selected = $model.convertToObject();
          else if(this.$$childTail.$select !== undefined)
            this.$$childTail.$select.selected = $model.convertToObject();
        }
        $scope.select = function(model){
          $scope.ngModel = model;
          $scope.setModel(model);
          triggerChange();
        };
        $scope.addList = function(model){
          var has = false;
          for(var i in $scope.array){
            if($scope.array[i].$data[$scope.searchField] === model.$data[$scope.searchField]){
              has = true;
              break;
            }
          }
          if(!has) {
            $scope.array.push(model);
            $scope.manager.add([model]);
          }
        };
        $scope.setManager = function(manager){
          $scope.array = [];
          $scope.manager = manager;
          $scope.array = manager.getCollection();
          $scope.select({});
        };
        $scope.refresh = function(q) {
          var params  = {
            data: [],
            limit: 30
          };

          if(!$scope.autoLoad){
            $scope.autoLoad = true;
            return;
          }

          if(angular.isDefined($scope.searchField)){
            params.data[0] = {
              field: $scope.searchField,
              condition: '=%',
              value: q
            };
          }
          return $scope.manager.find(params).then(function(response) {

            try{
              var modelA = $scope.manager.create();
              $scope.array = response;
              if(angular.isDefined($scope.ngModel) && $scope.ngModel !== null){
                modelA.set($scope.ngModel)
                $scope.selectModel(modelA);
              }
            }catch(e){
              console.log(e);
            }

          });

        };

      }]
    };
  }])
  .run(['$templateCache', function($templateCache) {

    $templateCache.put('select.html',
      "<div class=\"row\"><ui-select ng-model=\"ngModel\"\r" +
      "       theme=\"bootstrap\"\r" +
      "       reset-search-input=\"false\"\r" +
      "       on-select=\"selectModel($model)\"\r" +
      "       ng-disabled=\"disabled\"\r" +
      "       ng-class=\"{'col-sm-9': selectButton, 'col-sm-12': !selectButton}\"\r" +
      "       style=\"border-radius: 0px !important;\">\r" +
      "   <ui-select-match class=\"row\" placeholder=\"Selecione\">{{ $select.selected[displayField] }}</ui-select-match>\r" +
      "   <ui-select-choices repeat=\"rs in array track by $index\"\r" +
      "           refresh=\"refresh($select.search)\"\r" +
      "           refresh-delay=\"0\">\r" +
      "       <div ng-bind-html=\"rs.$data[displayField] | highlight: $select.search\"></div>\r" +
      "   </ui-select-choices>\r" +
      "</ui-select>\r" +
      "<input type=\"hidden\" ng-model=\"ngModel\" validator=\"{{validator}}\" class=\"form-control\" />\r" +
      "<button class=\"btn btn-info col-md-3 col-sm-3 col-xs-2\" ng-show=\"selectButton\" style=\"height: 33px\" type=\"button\" ng-disabled=\"disabled\"><span class=\"fa fa-search\"></span><span class=\"hidden-xs hidden-sm hidden-md hidden-lg\">Selecionar</span></button></div>"
    );

    $templateCache.put('windowSelect.html',
      "<div selectModel id=\"myModal\" tabindex=\"-1\" role=\"dialog\" class=\"modal\" ng-class=\"{in: animate}\" ng-style=\"{'z-index': 1050 + index*10, display: 'block'}\" ng-click=\"close($event)\">\r" +
      "   <div class=\"modal-dialog\" ng-class=\"{'modal-sm': size == 'sm', 'modal-lg': size == 'lg'}\">\n" +
      "       <div class=\"modal-content\">\r" +
      "           <div uib-modal-transclude style=\"background-color: #fff; padding: 0 15px 0 15px;\"></div>\r" +
      "           <div class=\"modal-footer\"><div class=\"btn-group pull-right\">\r" +
      "               <button class=\"btn btn-primary\" ng-click=\"$parent.select()\">Selecionar</button>\r" +
      "               <button class=\"btn btn-warning\" ng-click=\"$parent.cancel()\">Cancelar</button>\r" +
      "           </div></div>\r" +
      "       </div>\r" +
      "   </div>\r" +
      "</div>"
    );
  }]);