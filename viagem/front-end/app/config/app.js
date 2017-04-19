"use strict";

angular.module(APP_NAME, [
    'ui.router', // Routing
    'oc.lazyLoad', // ocLazyLoad
    'ui.utils.masks', // Angular input mask
    'blockUI',
    'ngSanitize',
    'Geral',
    'Localizacao',
    'ngCookies',
    'uiSwitch',
    'ui.sortable'
  ])
  .constant("API_URL", API_URL)
  .config(['$httpProvider', '$stateProvider', '$locationProvider', '$urlRouterProvider', '$ocLazyLoadProvider', 'RestangularProvider', '$qProvider', 'blockUIConfig', 'Generic.HttpDataAdapterProvider',
    function ($httpProvider, $stateProvider, $locationProvider, $urlRouterProvider, $ocLazyLoadProvider, RestangularProvider, $qProvider, blockUIConfig, HttpDataAdapterProvider) {

      HttpDataAdapterProvider.setBaseUrl('http://54.172.8.219:8080/viagem/rest/');
      //HttpDataAdapterProvider.setBaseUrl('http://10.211.55.42:8080/viagem/rest/');

      blockUIConfig.message = "Aguarde...";
      blockUIConfig.autoBlock = false;

      //$qProvider.errorOnUnhandledRejections(false);
      $ocLazyLoadProvider.config({
        // Set to true if you want to see what and when is dynamically loaded
        debug: false
      });

      // Rotas
      //$locationProvider.html5Mode(true);
      $urlRouterProvider.otherwise("/dashboard");

      $stateProvider
        .state('login', {
          url: "/login",
          views: {
            "": {
              templateUrl: "common/views/login.html",
              controller: "LoginCtrl",
              data: {
                pageTitle: 'Autenticacação do Usuário'
              },
            }
          },
          onEnter: function () {
            $('body').addClass("login");
          },
          onExit: function () {
            $('body').removeClass("login");
          }
        })

        .state('panel', {
          abstract: true,
          url: "^/",
          views: {
            "": {
              templateUrl: "common/views/panel.html"
            }
          }
        })

        .state('panel.home', {
          url: "^/dashboard",
          views: {
            "home@panel": {
              templateUrl: "common/views/main.html",
              controller: function(){},
              data: {
                pageTitle: 'Entrada'
              }
            }
          },
          resolve: {
            loadPlugin: function ($ocLazyLoad) {
              return $ocLazyLoad.load([
                {
                  serie: true,
                  name: 'angular-flot',
                  files: [
                    'common/js/plugins/flot/jquery.flot.js',
                    'common/js/plugins/flot/jquery.flot.time.js',
                    'common/js/plugins/flot/jquery.flot.tooltip.min.js',
                    'common/js/plugins/flot/jquery.flot.spline.js',
                    'common/js/plugins/flot/jquery.flot.resize.js',
                    'common/js/plugins/flot/jquery.flot.pie.js',
                    'common/js/plugins/flot/curvedLines.js',
                    'common/js/plugins/flot/angular-flot.js']
                }, {
                  name: 'angular.morris-chart',
                  files: ['common/js/plugins/morris/angular-morris.min.js']
                },
                {
                  name: 'angles',
                  files: ['common/js/plugins/chartJs/angles.js', 'common/js/plugins/chartJs/Chart.min.js']
                },
                {
                  name: 'angular-peity',
                  files: ['common/js/plugins/peity/jquery.peity.min.js', 'common/js/plugins/peity/angular-peity.js']
                }
              ]);
            }
          }
        })

        .state('panel.one', {
          url: '^/:controller.:action{id:(?:/[^/]+)?}?pag',
          views: {
            "home@panel": {
              templateProvider: ['$http', '$stateParams', '$q', function ($http, $stateParams, $q) {
                var deferred = $q.defer();

                $http.get('views/' + $stateParams.controller + '.' + $stateParams.action + '.html')
                  .then(function (data) {
                    deferred.resolve(data.data);
                  }, function (error) {
                    deferred.reject(error);
                  });

                return deferred.promise;
              }]
            }
          }
        })

        .state('panel.two', {
          url: '^/:path1/:controller.:action{id:(?:/[^/]+)?}?pag',
          views: {
            "home@panel": {
              templateProvider: ['$http', '$stateParams', '$q', function ($http, $stateParams, $q) {
                var deferred = $q.defer();

                $http.get('modules/' + $stateParams.path1 + '/view/' + $stateParams.controller + '.' + $stateParams.action + '.html')
                  .then(function (data) {
                    deferred.resolve(data.data);
                  }, function (error) {
                    deferred.reject(error);
                  });

                return deferred.promise;
              }],
              controllerProvider: ['$stateParams', 'utils', function ($stateParams, utils) {
                return utils.convertHyphenUpperCase($stateParams.path1) + '.Controller.' + utils.convertHyphenUpperCase($stateParams.controller)
              }]
            }
          }
        })

        .state('panel.three', {
          url: '^/:path1/:path2/:controller.:action{id:(?:/[^/]+)?}?pag',
          views: {
            "home@panel": {
              templateProvider: ['$http', '$stateParams', '$q', function ($http, $stateParams, $q) {
                var deferred = $q.defer();
                $http.get('modules/' + $stateParams.path1 + '/' + $stateParams.path2 + '/view/' + $stateParams.controller + '.' + $stateParams.action + '.html')
                  .then(function (data) {
                    deferred.resolve(data.data);
                  }, function (error) {
                    deferred.reject(error);
                  });
                return deferred.promise;
              }],
              controllerProvider: ['$stateParams', 'utils', function ($stateParams, utils) {
                return utils.convertHyphenUpperCase($stateParams.path1) + '.' + utils.convertHyphenUpperCase($stateParams.path2) + '.Controller.' + utils.convertHyphenUpperCase($stateParams.controller);
              }]
            }
          }
        })

        .state('panel.four', {
          url: '^/:path1/:path2/:path3/:controller.:action{id:(?:/[^/]+)?}?pag',
          views: {
            "main@home": {
              templateProvider: ['$http', '$stateParams', '$q', function ($http, $stateParams, $q) {
                var deferred = $q.defer();
                $http.get('views/' + $stateParams.path1 + '/' + $stateParams.path2 + '/' + $stateParams.path3 + '/' + $stateParams.controller + '.' + $stateParams.action + '.html')
                  .success(function (data) {
                    deferred.resolve(data);
                  });
                return deferred.promise;
              }],
              controllerProvider: ['$stateParams', 'utils', function ($stateParams, utils) {
                return utils.convertHyphenUpperCase($stateParams.path1) + '.' + utils.convertHyphenUpperCase($stateParams.path2) + '.' + utils.convertHyphenUpperCase($stateParams.path3) + '.Controller.' + utils.convertHyphenUpperCase($stateParams.controller) + 'Controller';
              }]
            }
          }
        });

    }
  ])

  .run(['$rootScope', '$state', 'EVENTS', 'blockUI', '$http', '$timeout', '$cookies', 'Generic.HttpDataAdapter', '$q',
    function ($rootScope, $state, EVENTS, blockUI, $http, $timeout, $cookies, DataAdapter, $q) {

      var user = null, token = null, ip = null, licenca = null, unidadeGestao = null, identity = null;

      $rootScope.$state = $state;
      identity = $cookies.getObject('identity');

      if (angular.isObject(identity)) {
        user = new Usuario;
        user.set(identity.user);

        // Recupera a imagem do usuário no localstorage, devido a imagem não estar no cookie
        user.set("imagem", localStorage.getItem("userImage"));

        token = identity.token.string;
        ip = identity.token.object.enderecoRemoto;
        licenca = identity.token.object.licenca;
        unidadeGestao = identity.token.object.unidadeGestao;

        DataAdapter.setParamsDefault({
          token: token
        });
      }

      $rootScope.$on(EVENTS.block, function () {
        blockUI.start();
      });

      $rootScope.$on(EVENTS.unblock, function () {
        blockUI.stop();
      });

      $rootScope.$on(EVENTS.wait, function () {
        $rootScope.$emit(EVENTS.block);
      });

      $rootScope.$on(EVENTS.ready, function () {
        $rootScope.$emit(EVENTS.unblock);
      });

      // Segmentado o processo de autorização e login para poder obter os dados do
      // usuário antes de enviar para página inicial
      $rootScope.$on(EVENTS.authorize, function (event, token) {
        DataAdapter.setParamsDefault({
          token: token
        });
      });

      //Usuário no Sistema
      $rootScope.$on(EVENTS.login, function (event, result) {
        user = result.user;
        token = result.token.string;
        ip = result.token.object.enderecoRemoto;
        licenca = result.token.object.licenca;
        unidadeGestao = result.token.object.unidadeGestao;

        var identityUser = result.user.convertToObject()

        localStorage.setItem("userImage", identityUser.imagem);

        // Retirado o base64 da imagem para armazenar cookies
        delete identityUser.imagem;

        $cookies.putObject('identity', {
          token: result.token,
          user: identityUser
        }, {
          expires: "session" // moment().add(1, 'hour').toDate() // Alterado para manter durante a sessão
        });

        var adapter = new DataAdapter,
          sinc = [];
        blockUI.start('Sincronizando Informações...');

        function go() {
          blockUI.stop();
          $state.go('panel.home');
        }

        adapter.request({
          resource: 'safra',
          action: 'sincronizar',
          data: {in_intervalo: true}
        }).then(function (result) {
          adapter.request({
            resource: 'contafinanceira',
            action: 'sincronizar',
            data: {in_intervalo: true}
          }).then(function (result) {
            adapter.request({
              resource: 'propriedade',
              action: 'sincronizar',
              data: {in_intervalo: true}
            }).then(function (result) {
              adapter.request({
                resource: 'documento',
                action: 'sincronizar',
                data: {in_intervalo: true}
              }).then(function (result) {
                go();
              }, function (error) {
                console.log(error);
                go();
              });
            }, function (error) {
              console.log(error);
              go();
            });
          }, function (error) {
            console.log(error);
            go();
          });
        }, function (error) {
          console.log(error);
          go();
        });

      });

      $rootScope.$on(EVENTS.logout, function () {
        user = null;
        token = null;
        ip = null;
        licenca = null;
        unidadeGestao = null;
        DataAdapter.setParamsDefault({});

        $cookies.remove('identity');
        $state.go('login');
      });

      $rootScope.$on('$stateChangeSuccess', function () {
        //if ($rootScope.isLogged() && $state.current.name === 'login') {
        //    $state.go('panel.home');
        //} else if (!$rootScope.isLogged() && ($state.current.name !== 'login' && $state.current.name !== 'register' && $state.current.name !== 'invitation')) {
        //    $state.go('login');
        //}
      });

      $rootScope.getUser = function () {
        return user;
      };

      $rootScope.getTOKEN = function () {
        return token;
      };

      $rootScope.isLogged = function () {
        return user && token;
      };
    }
  ]);