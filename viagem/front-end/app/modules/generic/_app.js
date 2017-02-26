
"use strict";

angular.module('Generic', [
    'restangular',
    'ui.bootstrap',
    'ngGrid',
    'ngMask',
    'ui.select',
    'dialogs.main',
    'jlareau.pnotify',
    'validator.directive'
  ])
  
  .constant("EVENTS", {
    block: "block",
    unblock: "unblock",
    wait: "wait",
    ready: "ready",
    login: "login",
    logout: "logout",
    islogged: "islogged",
    gridready: 'grid.ready',
    gridload: 'grid.load',
    gridbeforesave: 'grid.beforesave',
    gridaftersave: 'grid.aftersave',
    gridbeforecancel: 'grid.beforecancel',
    gridaftercancel: 'grid.aftercancel',
    authorize: 'authorize'
  })
  
  .config(['RestangularProvider', 'notificationServiceProvider', '$provide', 'dialogsProvider', '$validatorProvider', function(RestangularProvider, notificationServiceProvider, $provide, dialogsProvider, $validatorProvider){
    
    RestangularProvider.addResponseInterceptor(function(data, operation, what, url, response, deferred) {
      if(angular.isObject(data)){
        if(data.success === "true" || data.success === true){
          if(angular.isUndefined(data.data))
            deferred.reject({message: "Registro não encontrado."});
          else
            deferred.resolve(data.data);
        }else
          deferred.reject(data);
      }else
        deferred.reject({message: "Não foi possível identificar a resposta do servidor."});
    });
  
    notificationServiceProvider.setDefaults({
        history: false,
        delay: 4000,
        hide: true,
        buttons: {
            closer: true,
            sticker: false
        },
        mouse_reset: false,
        before_open: function(PNotify) {
          PNotify.get().click(function(){
            PNotify.remove();
          });
        }
    });
    notificationServiceProvider.setStack('top_right', 'stack-topright', {
        "dir1": "down",
        "dir2": "left",
        "push": "top"
    });
    notificationServiceProvider.setDefaultStack('top_right');

    /*$provide.decorator('alertDirective', ['$delegate', function($delegate){
        angular.extend($delegate[0].scope, {
            icon: '@'
        });
        return $delegate;
//    }]);*/

    dialogsProvider.useBackdrop('static');
    dialogsProvider.useEscClose(true);
    dialogsProvider.useCopy(false);
    dialogsProvider.setSize('md');
    
    //Regras de Validação
    $validatorProvider.register('required', {
      invoke: 'watch',
      validator: /.+/,
      error: 'Esse campo é obrigatório.'
    });
    $validatorProvider.register('number', {
      invoke: 'watch',
      validator: /^[-+]?[0-9]*[\.]?[0-9]*$/,
      error: 'Esse campo aceita apenas números.'
    });
    $validatorProvider.register('email', {
      invoke: 'blur',
      validator: /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
      error: 'E-mail inválido.'
    });
    $validatorProvider.register('url', {
      invoke: 'blur',
      validator: /((([A-Za-z]{3,9}:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)/,
      error: 'URL inválida.'
    });
    //
    
  }])
  
  .directive("ngCurrency", ['$filter', '$timeout', function($filter, $timeout){
        
    return {
      restrict: 'A',
      require: ['?ngModel'],
      scope: {
        valor: '=ngModel'
      },
      link: function(scope, element, attrs, ctrl){
        
        var qtdD    = attrs.decimal || 2;
          
        element.context.value = element.context.value || $filter('currency')(0, attrs.symbol, qtdD);
        element.context.onfocus = function(e){
          $timeout(function(){
            e.target.value = element.context.value;
          }, 10);          
        }
        
        if(angular.isDefined(ctrl[0]))
          scope.$watch('valor', change);
        else
          element.on('keyup', change);
        
        function change(){
          
          var value     = element.context.value,
              newV      = 0,
              qtdD      = qtdD || 2,
              decimals  = 0;
          
          //Quando o valor for numérico e não possuir todos os decimais
          if(Number(value).toString() !== "NaN"){
            value = Number(value).toFixed(qtdD);
          }

          value   = value ? value.replace(/[^(0-9).]/g, "").replace(/\./g, "") : '';

          if(String(value).length <= qtdD){
            while(String(value).length < qtdD)
              value = "0"+value;
            value = "0."+value;
          }else{
            value       = value+'!';
            decimals    = value.slice((qtdD+1)*-1);
            value       = value.replace(decimals, "."+decimals.slice(0, decimals.length-1));
          }

          value = Number(value);
          newV = $filter('currency')(value, attrs.symbol, qtdD);

          element.context.value = newV;
          if(angular.isDefined(ctrl[0]))
            ctrl[0].$setViewValue(value);
          
        }
              
      }
    };
      
  }])
  
  .filter('mask', function(){
    return function(value, mask){
      
      if(!angular.isDefined(value))
        return '';
      
      var value     = value.replace(/[^\d]/g, ''),
          formatter = new StringMask(mask);
      return formatter.apply(value);
      
    }
  });

angular
  .module("dialogs.main")
  .run(['$templateCache', '$interpolate', function($templateCache, $interpolate){
          
    // get interpolation symbol (possible that someone may have changed it in their application instead of using '{{}}')
    var startSym = $interpolate.startSymbol();
    var endSym = $interpolate.endSymbol();
          
    $templateCache.put('/dialogs/error.html','<div class="modal-header dialog-header-error"><button type="button" class="close" ng-click="close()">&times;</button><h4 class="modal-title text-danger"><i class="fa fa-exclamation-triangle" aria-hidden="true"></i> <span ng-bind-html="header"></span></h4></div><div class="modal-body text-danger" ng-bind-html="msg"></div><div class="modal-footer"><button type="button" class="btn btn-default" ng-click="close()">'+startSym+'"Fechar"'+endSym+'</button></div>');
    $templateCache.put('/dialogs/wait.html','<div class="modal-header dialog-header-wait"><h4 class="modal-title"><i class="fa fa-clock-o" aria-hidden="true"></i> '+startSym+'header'+endSym+'</h4></div><div class="modal-body"><p ng-bind-html="msg"></p><div class="progress progress-striped active"><div class="progress-bar progress-bar-info" ng-style="getProgress()"></div><span class="sr-only">'+startSym+'progress'+endSym+'% '+startSym+'"Completo"'+endSym+'</span></div></div>');
    $templateCache.put('/dialogs/notify.html','<div class="modal-header dialog-header-notify"><button type="button" class="close" ng-click="close()" class="pull-right">&times;</button><h4 class="modal-title text-info"><i class="fa fa-comment" aria-hidden="true"></i> '+startSym+'header'+endSym+'</h4></div><div class="modal-body text-info" ng-bind-html="msg"></div><div class="modal-footer"><button type="button" class="btn btn-primary" ng-click="close()">'+startSym+'"OK"'+endSym+'</button></div>');
    $templateCache.put('/dialogs/confirm.html','<div class="modal-header dialog-header-confirm"><button type="button" class="close" ng-click="no()">&times;</button><h4 class="modal-title"><i class="fa fa-question" aria-hidden="true"></i> '+startSym+'header'+endSym+'</h4></div><div class="modal-body" ng-bind-html="msg"></div><div class="modal-footer"><button type="button" class="btn btn-primary" ng-click="yes()">'+startSym+'"Sim"'+endSym+'</button><button type="button" class="btn btn-warning" ng-click="no()">'+startSym+'"Não"'+endSym+'</button></div>');
  
  }])
    