'use strict';

angular.module('Generic')
  .directive('datepicker', ['$uibModal', '$timeout', '$filter', function($modal, $timeout, $filter){
    return {
      restrict: 'E',
      templateUrl: function(el, attrs){
        return 'datePicker.html';
      },
      scope: {
        ngModel: "=",
        format: "=?",
        validator: "=?",
        ngReadonly: "=?"
      },
      link: function(scope, el, attrs){

        var input = jQuery(jQuery(el[0]).find(".date")[0]);
        input.datepicker({
          language: 'pt-BR',
          autoclose: true,
          todayHighlight: true,
          todayBtn: "linked",
          format: attrs.format,
          forceParse: false
        }).on('show', function(e){
          if(scope.ngReadonly)
            input.datepicker('hide');
        });
        scope.initValue = true;
        scope.date = scope.ngModel ? moment(scope.ngModel).format(attrs.format.toUpperCase()) : '';

        scope.$watch('ngModel', function(n){
          scope.date = scope.ngModel ? moment(scope.ngModel).format(attrs.format.toUpperCase()) : '';
        }, true);

        scope.$watch('date', function(v){
          if(angular.isDefined(v) && v !== ''){
            if(angular.isString(v)){
              var d = moment(v, attrs.format.toUpperCase());
              if(d.format(attrs.format.toUpperCase()).replace(/[\/-]/g, '') === v.replace(/[\/-]/g, '')){
                input.datepicker('update', d.toDate());
                scope.ngModel = d.toDate();
                scope.initValue = false;
              }else{
                scope.ngModel = '';
              }
            }
          }else if(v === ''){
            scope.ngModel = '';
          }
        });
      }
    };
  }])
  .run(['$templateCache', function($templateCache) {

    $templateCache.put('datePicker.html',
      "<div class=\"input-group date\">\r" +
      "   <input ng-model=\"date\" validator=\"validator\" ng-readonly=\"ngReadonly\" class=\"form-control\" />\r" +
      "   <span class=\"input-group-addon\"><i class=\"fa fa-calendar\"></i></span>\r" +
      "</div>"
    );

  }]);