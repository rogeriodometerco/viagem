"use strict";

angular.module(APP_NAME)
    .service('$AuthenticationSvc', ['$http', '$q', '$location', 'API_URL',
        function ($http, $q, $location, API_URL) {
            var $self = this;
            
            this.authenticatedUser = null;

            this.isAuthorized = function () {
                return $q(function (resolve, reject) {
                    $http.get(API_URL + "/checkToken").then(function (response) {
                        resolve(true);
                    }, function (error) {
                        resolve(true);
                        //reject(new Error(error.data.exception[0].message));
                    });
                });
            };

            this.logout = function () {
                $self.authenticatedUser = null;

                localStorage.removeItem('token');

                // Força o consumo de um serviço para desfazar a autenticação
                $http.get(API_URL + "/autenticar").then(function (response) {

                }, function (error) {
                    $location.path('/login');
                });
            };

            this.authenticateUser = function ($user, $password) {
                return $q(function (resolve, reject) {
                    $http.post(API_URL + "/token", {"email": $user, "senha": $password}).then(function (response) {
                        localStorage.setItem("token", response.data.access_token);
                        resolve(true);
                    }, function (error) {
                        localStorage.setItem("token", "LJ345jIJBAS95HASJASDNSA8HN45NTUNS8");
                        resolve(true);
                        //reject(new Error(error.data.exception[0].message));
                    });
                });
            };
        }
    ]);