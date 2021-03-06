
"use strict";

angular.module(APP_NAME)

    /**
     * pageTitle - Directive for set Page title - mata title
     */
    .directive('pageTitle', ['$rootScope', '$timeout',
      function($rootScope, $timeout) {
        return {
          link: function(scope, element) {
            var listener = function(event, toState, toParams, fromState, fromParams) {
              // Default title - load on Dashboard 1
              var title = 'Viagem | ';
              // Create your own title pattern
              if (toState.data && toState.data.pageTitle) title = 'Viagem | ' + toState.data.pageTitle;
              $timeout(function() {
                element.text(title);
              });
            };
            $rootScope.$on('$stateChangeStart', listener);
          }
        }
      }                         
    ])
    
    /**
     * sideNavigation - Directive for run metsiMenu on sidebar navigation
     */
    .directive('sideNavigation', ['$timeout',
      function($timeout) {
        return {
          restrict: 'A',
          link: function(scope, element) {
            // Call the metsiMenu plugin and plug it to sidebar navigation
            $timeout(function(){
              element.metisMenu();
            });
          }
        };
      }                             
    ])
    
    /**
     * iboxTools - Directive for iBox tools elements in right corner of ibox
     */
    .directive('iboxTools', ['$timeout',
      function($timeout) {
        return {
          restrict: 'A',
          scope: true,
          templateUrl: 'common/views/ibox_tools.html',
          controller: function ($scope, $element) {
            // Function for collapse ibox
            $scope.showhide = function () {
              var ibox = $element.closest('div.ibox');
              var icon = $element.find('i:first');
              var content = ibox.find('div.ibox-content');
              content.slideToggle(200);
              // Toggle icon from up to down
              icon.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
              ibox.toggleClass('').toggleClass('border-bottom');
              $timeout(function () {
                ibox.resize();
                ibox.find('[id^=map-]').resize();
              }, 50);
            },
            // Function for close ibox
            $scope.closebox = function () {
              var ibox = $element.closest('div.ibox');
              ibox.remove();
            }
          }
        };
      }                        
    ])
    
    /**
     * iboxTools with full screen - Directive for iBox tools elements in right corner of ibox with full screen option
     */
    .directive('iboxToolsFullScreen', ['$timeout',
      function($timeout) {
        return {
          restrict: 'A',
          scope: true,
          templateUrl: 'views/common/ibox_tools_full_screen.html',
          controller: function ($scope, $element) {
            // Function for collapse ibox
            $scope.showhide = function () {
              var ibox = $element.closest('div.ibox');
              var icon = $element.find('i:first');
              var content = ibox.find('div.ibox-content');
              content.slideToggle(200);
              // Toggle icon from up to down
              icon.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
              ibox.toggleClass('').toggleClass('border-bottom');
              $timeout(function () {
                ibox.resize();
                ibox.find('[id^=map-]').resize();
              }, 50);
            };
            // Function for close ibox
            $scope.closebox = function () {
              var ibox = $element.closest('div.ibox');
              ibox.remove();
            };
            // Function for full screen
            $scope.fullscreen = function () {
              var ibox = $element.closest('div.ibox');
              var button = $element.find('i.fa-expand');
              $('body').toggleClass('fullscreen-ibox-mode');
              button.toggleClass('fa-expand').toggleClass('fa-compress');
              ibox.toggleClass('fullscreen');
              setTimeout(function() {
                $(window).trigger('resize');
              }, 100);
            }
          }
        };
      }                                   
    ])  
    
    /**
     * minimalizaSidebar - Directive for minimalize sidebar
    */
    .directive('minimalizaSidebar', ['$timeout',
      function($timeout) {
        return {
          restrict: 'A',
          template: '<a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="" ng-click="minimalize()"><i class="fa fa-bars"></i></a>',
          controller: function ($scope, $element) {
            $scope.minimalize = function () {
              $("body").toggleClass("mini-navbar");
              if (!$('body').hasClass('mini-navbar') || $('body').hasClass('body-small')) {
                // Hide menu in order to smoothly turn on when maximize menu
                $('#side-menu').hide();
                // For smoothly turn on menu
                setTimeout(
                    function () {
                      $('#side-menu').fadeIn(400);
                    }, 200);
              }else if ($('body').hasClass('fixed-sidebar')){
                $('#side-menu').hide();
                setTimeout(
                    function () {
                      $('#side-menu').fadeIn(400);
                    }, 100);
              } else {
                // Remove all inline style from jquery fadeIn function to reset menu state
                $('#side-menu').removeAttr('style');
              }
            }
          }
        };
      }                                 
    ]);

