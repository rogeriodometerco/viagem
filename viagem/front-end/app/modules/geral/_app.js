
"use strict";

angular.module('Geral', [
    'Generic'
  ])

  .config(['$validatorProvider', function($validatorProvider){

    $validatorProvider.register('placa', {
      invoke: 'watch',
      init: function (scope, element, attrs, $injector) {
        var $filter = $injector.get('$filter'),
          value = angular.navigateObject(scope.$parent, attrs.ngModel);
        if (value) {
          value = $filter('placa')(value);
          element.val(value);
        }
      },
      validator: function (value, scope, element, attrs, $injector) {

        var $filter = $injector.get('$filter'),
            regexp  = new RegExp(/^([A-Za-z]{3})-([0-9]{4})/);
        value = $filter('placa')(value);
        element.val(value);

        if (regexp.test(value) || value === '') {
          return true;
        }

        return false;

      },
      error: 'Placa inválida.'
    });
    //
  }])

  .filter('placa', ['$filter', function($filter){
    return function (value) {

      return $filter('mask')(value, 'SSS-0000');

    }
  }]);