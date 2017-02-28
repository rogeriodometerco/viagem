"use strict";

angular.module(APP_NAME)
    // Controlador principal
    .controller('MainCtrl', ['$scope', '$uibModal', '$AuthenticationSvc', 'EVENTS', function($scope, $uibModal, $AuthenticationSvc, EVENTS) {    
        $scope.logout = function() {
          $scope.$emit(EVENTS.logout);
        };

        $scope.invitation = function() {        
            $uibModal.open({
                templateUrl: 'common/views/send-invitation.html',
                controller: ['$scope', '$uibModalInstance', '$timeout', 'EVENTS', '$validator', '$http', 'Generic.HttpUnifaceDataAdapter', 'notificationService',
                    function ($scope, $uibModalInstance, $timeout, EVENTS, $validator, $http, DataAdapter, nS) {
                        var adapter = new DataAdapter;

                        $scope.save = function () {
                            $validator.validate($scope).success(function () {
                                $scope.$emit(EVENTS.wait);
                                
                                adapter.request({
                                    resource: 'convite',
                                    action: 'emitir',
                                    data: {
                                        tp_matricula: $scope.tipoMatricula,
                                        cd_matricula: $scope.matricula.replace(/[-\.]/g, ''),
                                        nr_cpf: $scope.cpf.replace(/[-\.]/g, '')
                                    }
                                }).then(function (result) {
                                    nS.success('Convite enviado com sucesso!');
                                    $uibModalInstance.close();
                                    $scope.$emit(EVENTS.ready);
                                }, function (error) {
                                    nS.error(error.message);
                                    $scope.$emit(EVENTS.ready);
                                });
                            }).error(function () {

                            });
                        };

                        $scope.cancel = function () {
                            $uibModalInstance.dismiss("cancel");
                        };
                    }],
                size: 'md'
            });
        };
      
        $scope.notificacoes = [];
      
        $scope.notificacoes.push({
          id: 1,
          descricao: "Notificação 1",
          icone: "fa-envelope"
        });

        $scope.notificacoes.push({
          id: 2,
          descricao: "Notificação 2",
          icone: "fa-upload"
        });

        $scope.notificacoes.push({
          id: 3,
          descricao: "Notificação 3",
          icone: "fa-twitter"
        });    
    }])
  
    // Controlador genérico do modal de confirmação
    .controller('ConfirmModalCtrl', ['$scope', '$uibModalInstance', 'modalOptions', function($scope, $uibModalInstance, modalOptions) {
        $scope.modal = modalOptions;

        $scope.close = function () {
            $uibModalInstance.dismiss("cancel");
        };

        $scope.modalClick = function (value) {
            $uibModalInstance.close(value);
        };
    }])

    // Controlador da Planejamento de Safra
    .controller('PlanejamentoCtrl', ['$scope', '$filter', 'Generic.HttpUnifaceDataAdapter', 'EVENTS', 'notificationService', '$q', '$uibModal', '$rootScope', function ($scope, $filter, DataAdapter, EVENTS, nS, $q, $uibModal, $rootScope){
        var adapter = new DataAdapter;
        var chartOptions = {
            series: {
                bars: {
                    show: true,
                    horizontal: true,
                    barWidth: 0.6,
                    fill: true,
                    fillColor: {
                        colors: [
                            {
                                opacity: 0.8
                            },
                            {
                                opacity: 0.8
                            }
                        ]
                    }
                }
            },
            xaxis: {
                tickFormatter: function (v, axis) {
                    return $filter('currency')(v, 'R$ ', 0);
                },
                tickSize: 10000,
                autoscaleMargin: 0.1
            },
            yaxis: {
                autoscaleMargin: 0.1
            },
            legend: {
                show: false
            },
            colors: ["#1ab394"]
            ,
            tooltip: true,
            tooltipOpts: {
                content: "%x"
            },
            grid: {
                color: "#999999",
                hoverable: true,
                clickable: true,
                tickColor: "#D4D4D4",
                borderWidth: 0
            }            
        };
            
        $scope.planejamento = {};
        $scope.grupos = [];
                   
        $scope.$emit(EVENTS.wait);
    
        adapter.request({
            resource: 'grupofinanceiro',
            action: 'listarcompleto'
        }).then(function(data){
            $scope.grupos = data.resultado;        
            $scope.$emit(EVENTS.ready);
        }, function (error) {
            nS.error(error.message);
            $scope.$emit(EVENTS.ready);
        });
                
        $scope.atualizarGraficos = function(){
            $scope.planejamento.vl_total = 0;
            
            $scope.planejamento.custoInsumo = {
                labels: [],
                data: [],
                chartOptions: angular.copy(chartOptions)
            };

            $scope.planejamento.custoOperacao = {
                labels: [],
                data: [],
                chartOptions: angular.copy(chartOptions)
            };

            $scope.planejamento.custoOutros = {
                labels: [],
                data: [],
                chartOptions: angular.copy(chartOptions)
            };

            $scope.planejamento.custoGeral = {
                chartData: []
            };
                
            var insumoIndex = 1;
            var operacaoIndex = 1;
            var outrosIndex = 1;

            angular.forEach($scope.grupos, function(grupo){   
                grupo.vl_total = 0;   
                grupo.vl_hectare = 0;
                grupo.vl_alqueire = 0;
                
                angular.forEach(_.sortBy(grupo.contas, "vl_conta"), function(conta){
                    conta.vl_hectare = 0;
                    conta.vl_alqueire = 0;
                                        
                    if (conta.vl_conta > 0){
                        // Considera apenas despesas com insumos agrícola
                        if (grupo.cd_grupo == 6){                    
                            $scope.planejamento.custoInsumo.data.push([conta.vl_conta, insumoIndex]);
                            $scope.planejamento.custoInsumo.labels.push([insumoIndex, conta.nm_conta]);  

                            insumoIndex++;
                        } else if (grupo.cd_grupo == 3){                   
                            $scope.planejamento.custoOperacao.data.push([conta.vl_conta, operacaoIndex]);
                            $scope.planejamento.custoOperacao.labels.push([operacaoIndex, conta.nm_conta]);  

                            operacaoIndex++;                            
                        } else {                                            
                            $scope.planejamento.custoOutros.data.push([conta.vl_conta, outrosIndex]);
                            $scope.planejamento.custoOutros.labels.push([outrosIndex, conta.nm_conta]);  

                            outrosIndex++;      
                        };

                        $scope.planejamento.custoGeral.chartData.push({
                            label: conta.nm_conta,
                            data: conta.vl_conta
                        });
                                               
                        if ($scope.planejamento.area > 0){
                            conta.vl_hectare = conta.vl_conta / $scope.planejamento.area;
                            conta.vl_alqueire = conta.vl_hectare * 2.42;                            
                        }
                    }
                    
                    grupo.vl_total += conta.vl_conta;
                    grupo.vl_hectare += conta.vl_hectare;
                    grupo.vl_alqueire += conta.vl_alqueire;                    
                });    
                
                $scope.planejamento.vl_total += grupo.vl_total;
            });
            
            angular.forEach($scope.grupos,function(grupo){
                grupo.pe_grupo = grupo.vl_total ? (grupo.vl_total / $scope.planejamento.vl_total) * 100 : 0;
                
                angular.forEach(grupo.contas, function(conta){                    
                    conta.pe_conta = conta.vl_conta ? (conta.vl_conta / $scope.planejamento.vl_total) * 100 : 0; 
                });
            });
                        
            $scope.planejamento.qt_pontoequilibrio = $scope.planejamento.vl_total && $scope.planejamento.preco ? $scope.planejamento.vl_total / $scope.planejamento.preco : 0;
            $scope.planejamento.vl_custoarea = ($scope.planejamento.vl_total / $scope.planejamento.area) / $scope.planejamento.preco;      
            $scope.planejamento.vl_custosaca = ($scope.planejamento.vl_total / ($scope.planejamento.area * $scope.planejamento.produtividade));
            $scope.planejamento.vl_lucroarea = $scope.planejamento.produtividade - $scope.planejamento.vl_custoarea;
            $scope.planejamento.vl_lucrosaca = $scope.planejamento.preco - $scope.planejamento.vl_custosaca;
            $scope.planejamento.vl_receitabruta = ($scope.planejamento.area * $scope.planejamento.produtividade) * $scope.planejamento.preco;
            $scope.planejamento.vl_receitaliquida = $scope.planejamento.vl_receitabruta - $scope.planejamento.vl_total;
            $scope.planejamento.pe_lucratividade = ($scope.planejamento.vl_receitaliquida / $scope.planejamento.vl_receitabruta) * 100;            
            $scope.planejamento.pe_rentabilidade =  $scope.planejamento.vl_receitaliquida / $scope.planejamento.vl_total;

            // Adiciona os dados no formato do gráfico
            $scope.planejamento.custoInsumo.chartData = [{
                label: "Insumos Agrícolas",
                data: $scope.planejamento.custoInsumo.data
            }];

            // Configura as opções do gráfico e adiciona as labels
            $scope.planejamento.custoInsumo.chartOptions.yaxis.ticks = $scope.planejamento.custoInsumo.labels;
            $scope.planejamento.custoInsumo.chartOptions.xaxis.tickSize = parseFloat(_.chain($scope.planejamento.custoInsumo.data).map(function (o) { return o[0] }).max().value() / 3).toFixed(0);

            // Adiciona os dados no formato do gráfico
            $scope.planejamento.custoOperacao.chartData = [{
                label: "Operações",
                data: $scope.planejamento.custoOperacao.data
            }];

            // Configura as opções do gráfico e adiciona as labels
            $scope.planejamento.custoOperacao.chartOptions.yaxis.ticks = $scope.planejamento.custoOperacao.labels;
            $scope.planejamento.custoOperacao.chartOptions.xaxis.tickSize = parseFloat(_.chain($scope.planejamento.custoOperacao.data).map(function (o) { return o[0] }).max().value() / 3).toFixed(0);

            $scope.planejamento.custoOutros.chartData = [{
                label: "Outros",
                data: $scope.planejamento.custoOutros.data
            }];

            // Configura as opções do gráfico e adiciona as labels
            $scope.planejamento.custoOutros.chartOptions.yaxis.ticks = $scope.planejamento.custoOutros.labels;
            $scope.planejamento.custoOutros.chartOptions.xaxis.tickSize = parseFloat(_.chain($scope.planejamento.custoOutros.data).map(function (o) { return o[0] }).max().value() / 3).toFixed(0);

            // Configuração do gráfico de pizza
            $scope.planejamento.custoGeral.chartOptions = {
                series: {
                    pie: {
                        show: true
                    }
                },
                grid: {
                    hoverable: true
                },
                legend: {
                    show: true,
                    noColumns: 2,
                    sorted: "descending",
                    labelFormatter: function (legend, series) {
                        return series.percent.toFixed(2) + "% - " + legend + "  ";
                    }
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
                    shifts: {
                        x: 20,
                        y: 0
                    },
                    defaultTheme: false
                }
            };   
        };
    }])

    // Controlador da Dashboard
    .controller('DashBoardCtrl', ['$scope', '$filter', 'Generic.HttpUnifaceDataAdapter', 'EVENTS', 'notificationService', '$q', '$uibModal', '$rootScope', function ($scope, $filter, DataAdapter, EVENTS, nS, $q, $uibModal, $rootScope){
        var adapter = new DataAdapter;
        var produtivArray = [];
        
        $scope.valorSoja = 63.50;
        $scope.culturas = [];                
        $scope.$emit(EVENTS.wait);
        
        var promiseProdutiv = adapter.request({
            resource: 'dashboard',
            action: 'buscarprodutiv'
        });
        
        promiseProdutiv.then(function(result){
            produtivArray = result;
        }, function (error) {
            nS.error(error.message);
            $scope.$emit(EVENTS.ready);
        });

        var promiseDocumento = adapter.request({
            resource: 'dashboard',
            action: 'buscardocumento'
        });
                
        promiseDocumento.then(function (result) {        
            $scope.culturas = result;
            
            var chartOptions = {
                series: {
                    bars: {
                        show: true,
                        horizontal: true,
                        barWidth: 0.6,
                        fill: true,
                        fillColor: {
                            colors: [
                                {
                                    opacity: 0.8
                                },
                                {
                                    opacity: 0.8
                                }
                            ]
                        }
                    }
                },
                xaxis: {
                    tickFormatter: function (v, axis) {
                        return $filter('currency')(v, 'R$ ', 0);
                    },
                    tickSize: 10000,
                    autoscaleMargin: 0.1
                },
                yaxis: {
                    autoscaleMargin: 0.1
                },
                legend: {
                    show: false
                },
                colors: ["#1ab394"]
                ,
                tooltip: true,
                tooltipOpts: {
                    content: "%x"
                },
                grid: {
                    color: "#999999",
                    hoverable: true,
                    clickable: true,
                    tickColor: "#D4D4D4",
                    borderWidth: 0
                }            
            };

            angular.forEach($scope.culturas, function(cultura){
                cultura.custoInsumo = {                    
                    labels: [],
                    data: [],
                    chartOptions: angular.copy(chartOptions)
                };
                
                cultura.custoOperacao = {                   
                    labels: [],
                    data: [],
                    chartOptions: angular.copy(chartOptions)
                };
                
                cultura.custoOutros = {                   
                    labels: [],
                    data: [],
                    chartOptions: angular.copy(chartOptions)
                };
                
                cultura.custoGeral = {
                    chartData: []
                };
                
                var insumoIndex = 1;
                var operacaoIndex = 1;
                var outrosIndex = 1;
                
                angular.forEach(cultura.grupos, function(grupo){                    
                    angular.forEach(_.sortBy(grupo.contas, "vl_totliquido"), function(conta){
                        // Considera apenas despesas com insumos agrícola
                        if (grupo.cd_grupo == 6){                    
                            cultura.custoInsumo.data.push([conta.vl_totliquido, insumoIndex]);
                            cultura.custoInsumo.labels.push([insumoIndex, conta.nm_conta]);  

                            insumoIndex++;
                        } else if (grupo.cd_grupo == 3){                   
                            cultura.custoOperacao.data.push([conta.vl_totliquido, operacaoIndex]);
                            cultura.custoOperacao.labels.push([operacaoIndex, conta.nm_conta]);  

                            operacaoIndex++;                            
                        } else {                                            
                            cultura.custoOutros.data.push([conta.vl_totliquido, outrosIndex]);
                            cultura.custoOutros.labels.push([outrosIndex, conta.nm_conta]);  

                            outrosIndex++;      
                        };

                        cultura.custoGeral.chartData.push({
                            label: conta.nm_conta,
                            data: conta.vl_totliquido,
                            // color: colorArray[colorIndex]
                        });
                    });                    
                });
                                                
                // Adiciona os dados no formato do gráfico
                cultura.custoInsumo.chartData = [{
                    label: "Insumos Agrícolas",
                    data: cultura.custoInsumo.data
                }];
            
                // Configura as opções do gráfico e adiciona as labels
                cultura.custoInsumo.chartOptions.yaxis.ticks = cultura.custoInsumo.labels;
                cultura.custoInsumo.chartOptions.xaxis.tickSize = parseFloat(_.chain(cultura.custoInsumo.data).map(function(o){ return o[0] }).max().value() / 3).toFixed(0);
                                  
                // Adiciona os dados no formato do gráfico
                cultura.custoOperacao.chartData = [{
                    label: "Operações",
                    data: cultura.custoOperacao.data
                }];
            
                // Configura as opções do gráfico e adiciona as labels
                cultura.custoOperacao.chartOptions.yaxis.ticks = cultura.custoOperacao.labels;
                cultura.custoOperacao.chartOptions.xaxis.tickSize = parseFloat(_.chain(cultura.custoOperacao.data).map(function(o){ return o[0] }).max().value() / 3).toFixed(0);
                               
                cultura.custoOutros.chartData = [{
                        label: "Outros",
                        data: cultura.custoOutros.data
                }];
            
                // Configura as opções do gráfico e adiciona as labels
                cultura.custoOutros.chartOptions.yaxis.ticks = cultura.custoOutros.labels;
                cultura.custoOutros.chartOptions.xaxis.tickSize = parseFloat(_.chain(cultura.custoOutros.data).map(function(o){ return o[0] }).max().value() / 3).toFixed(0);
                                
                // Configuração do gráfico de pizza
                cultura.custoGeral.chartOptions = {
                    series: {
                        pie: {
                            show: true
                        }
                    },
                    grid: {
                        hoverable: true
                    },
                    legend: {
                        show: true,
                        noColumns: 2,
                        sorted: "descending",
                        labelFormatter: function(legend, series){
                            return series.percent.toFixed(2) + "% - " + legend + "  ";                            
                        }
                    },
                    tooltip: true,
                    tooltipOpts: {
                        content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
                        shifts: {
                            x: 20,
                            y: 0
                        },
                        defaultTheme: false
                    }
                };
            });          
                        
            $scope.$emit(EVENTS.ready);  
        }, function (error) {
            nS.error(error.message);
            $scope.$emit(EVENTS.ready);
        });            
        
        $q.all([promiseDocumento, promiseProdutiv]).then(function(){
            angular.forEach(produtivArray, function(produtiv){
                var cultura = $scope.culturas.filter(function(cultura){
                    return cultura.cd_cultura == produtiv.cd_cultura;
                })[0];
                
                cultura.qt_area = parseFloat(produtiv.qt_area);
                cultura.qt_entregue = parseFloat(produtiv.qt_entregue / 60).toFixed(2);                
                cultura.qt_produtiv = parseFloat((produtiv.qt_entregue / produtiv.qt_area) / 60).toFixed(2); 
                cultura.qt_pontoequilibrio = parseFloat(cultura.vl_totliquido / $scope.valorSoja).toFixed(2);                
                cultura.vl_custoarea = parseFloat((cultura.vl_totliquido / produtiv.qt_area) / $scope.valorSoja);               
                cultura.vl_custosaca = parseFloat(cultura.vl_totliquido / cultura.qt_entregue);
                cultura.vl_lucroarea = parseFloat(cultura.qt_produtiv - cultura.vl_custoarea);
                cultura.vl_lucrosaca = parseFloat($scope.valorSoja - cultura.vl_custosaca);
                cultura.vl_receitabruta = parseFloat(cultura.qt_entregue * $scope.valorSoja);
                cultura.vl_receitaliquida = parseFloat(cultura.vl_receitabruta - cultura.vl_totliquido);
                cultura.pe_lucratividade = parseFloat((cultura.vl_receitaliquida / cultura.vl_receitabruta) * 100);
                cultura.pe_rentabilidade = parseFloat((cultura.vl_receitaliquida / cultura.vl_totliquido));
            });
        }, function(){            
            $scope.$emit(EVENTS.ready);
        });
        
        $scope.helpResumo = function(){
            $rootScope.modalInstance = $uibModal.open({
                templateUrl: 'common/views/dashboard.help.resumoprod.html',            
                size: "md"
            });
        
            $rootScope.fechar = function(){
                $scope.modalInstance.close();
            };
        };
    }])
  
    // Controlador da tela de login
    .controller('LoginCtrl', ['$scope', '$state', 'NPL.Pessoa.Service.Usuario', 'notificationService', '$validator', 'EVENTS', '$timeout',
        function ($scope, $state, UsuarioService, nS, $validator, EVENTS, $timeout) {
            var service = new UsuarioService,
                w = $(window),
                box = $('.loginscreen'),
                size = 0;

            $timeout(function () {
                box.tooltip({
                    selector: "[data-toggle=tooltip]",
                    container: "body"
                });
            }, 100);

            size = (w.height() / 2) - (box.height() / 2) - 70;
            box.css('marginTop', size > 10 ? size : 10);
            
            function centerize() {
                $timeout(function () {
                    size = (w.height() / 2) - (box.height() / 2) - 70;
                    box.animate({
                        marginTop: size > 10 ? size : 10
                    }, 300);
                }, 100);
            };
            
            w.resize(function () {
                centerize();
            });

            $scope.unidadesGestao = [];
            $scope.hasPassword = null;
            $scope.hasCode = false;
            $scope.unidadeGestaoV = '';

            $scope.data = {
                cpf: '',
                unidadeGestao: '',
                licenca: '',
                senha: '',
                confirmaSenha: '',
                codigoAcesso: ''
            };

            // Se não houver Unidades de Gestão, esconde Senha
            $scope.$watch('unidadesGestao', function (n, o) {
                $scope.hasPassword = null;
                $scope.hasCode = false;
                
                if (o.length !== n.length){
                    centerize();
                }
            });

            //Se o CPF for válido, consulta as Unidades de Gestão
            $scope.$watch(function (scope) {
                return $scope.loginForm.cpf.$valid;
            }, function (n, o) {
                if (n) {
                    $scope.findUnidadesGestao($scope.data.cpf);
                } else {
                    $scope.unidadesGestao = [];
                }
            });

            //De acordo com a Unidade de Gestão, verifica se há senha ou não
            $scope.$watch(function (scope) {
                return $scope.unidadeGestaoV;
            }, function (n, o) {
                if (n != "") {
                    var div = n.split('-');
                    
                    for (var i in $scope.unidadesGestao) {
                        var uG = $scope.unidadesGestao[i];
                        
                        if (uG.id_undgestao == div[0] && uG.id_licencanpl == div[1]) {
                            $scope.hasPassword = uG.in_primeiroacesso === 0 ? true : false;
                            if($scope.hasPassword){
                              $timeout(function(){
                                $('#passwordLogin').focus();
                              });
                            }
                            $scope.data.licenca = uG.id_licencanpl;
                            $scope.data.unidadeGestao = uG.id_undgestao;
                        }
                    }
                } else{
                    $scope.hasPassword = null;
                }
    
                centerize();
            });

            //Registra um validador para o confirma senha
            $validator.register('confirmPassword', {
                invoke: 'watch',
                validator: function (value, scope, element, attrs, $injector) {
                    return value === scope.data.senha;
                },
                error: 'Confirmação de Senha incorreta.'
            });

            $scope.findUnidadesGestao = function (cpf) {
                if (cpf != "") {
                    $scope.$emit(EVENTS.wait);
                    
                    service.getUnidadesGestaoByCPF(cpf).then(function (result) {
                        $scope.$emit(EVENTS.ready);
                        $scope.unidadesGestao = result;
                        
                        if (result.length == 0){
                            nS.error('CPF não é um Usuário.');
                        }else if($scope.unidadesGestao.length == 1){
                          $scope.unidadeGestaoV = $scope.unidadesGestao[0].id_undgestao+"-"+$scope.unidadesGestao[0].id_licencanpl;
                        }
                    }, function (error) {
                        $scope.$emit(EVENTS.ready);
                        console.log(error.message);
                        
                        nS.error('Não foi possível recuperar a lista de Unidade de Gestão.');
                        
                        $scope.unidadesGestao = [];
                    });
                } else {
                    $scope.unidadesGestao = [];
                }
            };

            $scope.codeGenerate = function () {
                $scope.hasCode = true;
                
                $timeout(function () {
                    $($scope.loginForm.codigoAcesso.$$element).focus();
                });
                
                centerize();
            };

            $scope.register = function () {
                return $validator.validate($scope).success(function () {
                    try {
                        $scope.$emit(EVENTS.wait);
                        
                        service.registerPassword($scope.data.cpf, $scope.data.unidadeGestao, $scope.data.licenca, $scope.data.senha, $scope.data.codigoAcesso)
                                .then(function (result) {
                                    $scope.$emit(EVENTS.ready);
                                    nS.success('Bem vindo ao NPL.');
                                    $scope.$emit(EVENTS.login, result);
                                }, function (error) {
                                    $scope.$emit(EVENTS.ready);
                                    nS.error(error.message);
                                });
                    } catch (e) {
                        console.error(e);
                    }
                }).error(function (error) {

                });
            };

            $scope.authenticate = function () {
                if (!$scope.hasPassword) {
                    $scope.register();
                    return;
                }

                $scope.data.confirmaSenha = $scope.data.senha;
                $scope.data.codigoAcesso = '1';

                return $validator.validate($scope).success(function () {
                    try {
                        $scope.$emit(EVENTS.wait);
                        
                        service.authenticate($scope.data.cpf, $scope.data.unidadeGestao, $scope.data.licenca, $scope.data.senha)
                                .then(function (result) {
                                    $scope.$emit(EVENTS.ready);
                                    nS.success('Bem vindo ao NPL.');
                                    $scope.$emit(EVENTS.login, result);
                                }, function (error) {
                                    $scope.$emit(EVENTS.ready);
                                    nS.error(error.message);
                                });
                    } catch (e) {
                        console.log(e);
                    }
                }).error(function (error) {
                    $scope.data.confirmaSenha = '';
                    $scope.data.codigoAcesso = '';
                });
            };
        }
    ])
  
  // Controlador da tela de Registro
  .controller('RegisterCtrl', ['$scope', '$timeout',
    function($scope, $timeout) {
    
      var w       = $(window),
          box     = $('.register-box'),
          size    = 0;
      
      $scope.data = {
        tipoMatricula:  '',
        matricula:      '',
        cpf:            '',
        unidadeGestao:  '',
        senha:          '',
        confirmaSenha:  '',
        codigoAcesso:   ''
      }
      
      $scope.hasCode = false;
      
      $timeout(function(){
        box.tooltip({
          selector: "[data-toggle=tooltip]",
          container: "body"
        });
      }, 100);
      
      //Centralizando Box
      box.hide();
      $timeout(function(){
        size = (w.height()/2)-(box.height()/2)-10;
        box.css({
          marginTop: size > 10 ? size : 10,
          display: 'block'
        });
      }, 100);
      w.resize(function(){
        size = (w.height()/2)-(box.height()/2)-10;
        box.animate({
          marginTop: size > 10 ? size : 10
        }, 300);
      });
      //
      
      $scope.$on("matriculaIsValid", function(){
        size = (w.height()/2)-(688/2)-10
        box.animate({
          height: 688,
          marginTop: size > 10 ? size : 10
        }, 300);
        $timeout(function(){
          $scope.hasCode = true;
          $scope.$broadcast("initForm");
        }, 300);
      });
      
    }                         
  ])

  // Controlador do Form de Validação de Matrícula
  .controller('MatriculaValidateCtrl', ['$scope', 'NPL.Pessoa.Service.Usuario', 'notificationService', '$validator', 'EVENTS',
    function($scope, UsuarioService, nS, $validator, EVENTS) {
    
      var service = new UsuarioService,
          w       = $(window),
          box     = $('.register-box');
      
      //Registra um validador para o confirma senha
      $validator.register('confirmPassword', {
        invoke: 'watch',
        validator: function(value, scope, element, attrs, $injector){
          return value === scope.data.senha;
        },
        error: 'Confirmação de Senha incorreta.'
      });
      
      //Valida matrícula na Coamo e manda um código para validar cadastro
      $scope.matriculaValid = function(){
        
        return $validator.validate($scope).success(function() {
          $scope.$emit(EVENTS.wait);
          service.requestAccess($scope.data.tipoMatricula, $scope.data.matricula, $scope.data.cpf).then(function(){
            $scope.$emit(EVENTS.ready);
            $scope.$emit("matriculaIsValid");
            box.find('#btnMatriculaValid').addClass('fadeOutUp');
          }, function(error){
            $scope.$emit(EVENTS.ready);
            nS.error(error.message);
          });
        }).error(function(error){
        });
      };
      
    }                         
  ])

  // Controlador da Finalização do Registro
  .controller('FinishRegisterCtrl', ['$scope', 'NPL.Pessoa.Service.Usuario', 'notificationService', '$validator', 'EVENTS', '$timeout',
    function($scope, UsuarioService, nS, $validator, EVENTS, $timeout) {
    
      var service = new UsuarioService,
          w       = $(window),
          box     = $('.register-box');
      
      $scope.$on("initForm", function(){
        $timeout(function(){
          $($scope.registerForm.codigoAcesso.$$element).focus();
        }, 100);
      });
      
      //Valida matrícula na Coamo e manda um código para validar cadastro
      $scope.matriculaValid = function(){
        $scope.$emit(EVENTS.wait);
        service.requestAccess($scope.data.tipoMatricula, $scope.data.matricula, $scope.data.cpf).then(function(){
          $scope.$emit(EVENTS.ready);
          $scope.$emit("matriculaIsValid");
          box.find('#btnMatriculaValid').addClass('fadeOutUp');
        }, function(error){
          $scope.$emit(EVENTS.ready);
          nS.error(error.message);
        });
      };
      
      $scope.register = function () {
        return $validator.validate($scope).success(function() {
          $scope.$emit(EVENTS.wait);
          service.activateLicence(
              $scope.data.tipoMatricula, 
              $scope.data.matricula, 
              $scope.data.cpf,
              $scope.data.unidadeGestao,
              $scope.data.senha,
              $scope.data.codigoAcesso)
          .then(function(result){
            $scope.$emit(EVENTS.ready);
            nS.success('Bem vindo ao NPL.');
            $scope.$emit(EVENTS.login, result);
          }, function(error){
            $scope.$emit(EVENTS.ready);
            nS.error(error.message);
          });
        }).error(function(error) {
        });
        
      };
      
    }    

  ])
  
  // Controlador da tela de Aceite de Convite
  .controller('ConviteCtrl', ['$scope', '$timeout', '$validator', 'NPL.Pessoa.Service.Usuario', 'EVENTS', 'notificationService',
    function($scope, $timeout, $validator, UsuarioService, EVENTS, nS) {
    
      var service = new UsuarioService,
          w       = $(window),
          box     = $('.register-box'),
          size    = 0;
      
      //Registra um validador para o confirma senha
      $validator.register('confirmPassword', {
        invoke: 'watch',
        validator: function(value, scope, element, attrs, $injector){
          return value === scope.data.senha;
        },
        error: 'Confirmação de Senha incorreta.'
      });
      
      $scope.data = {
        tipoMatricula:  '',
        matricula:      '',
        cpf:            '',
        senha:          '',
        confirmaSenha:  '',
        codigoAcesso:   ''
      }
      
      //Centralizando Box
      box.hide();
      $timeout(function(){
        size = (w.height()/2)-(box.height()/2)-10;
        box.css({
          marginTop: size > 10 ? size : 10,
          display: 'block'
        });
      }, 100);
      w.resize(function(){
        size = (w.height()/2)-(box.height()/2)-10;
        box.animate({
          marginTop: size > 10 ? size : 10
        }, 300);
      });
      //     

      
      $scope.register = function () {
        return $validator.validate($scope).success(function() {
          $scope.$emit(EVENTS.wait);
          service.acceptInvitation(
              $scope.data.tipoMatricula, 
              $scope.data.matricula, 
              $scope.data.cpf,
              $scope.data.senha,
              $scope.data.codigoAcesso)
          .then(function(result){
            $scope.$emit(EVENTS.ready);
            nS.success('Bem vindo ao NPL.');
            $scope.$emit(EVENTS.login, result);
          }, function(error){
            $scope.$emit(EVENTS.ready);
            nS.error(error.message);
          });
        }).error(function(error) {
        });
        
      };
      
    }                         
  ]);

