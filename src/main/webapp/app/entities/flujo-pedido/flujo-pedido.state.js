(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('flujo-pedido', {
            parent: 'entity',
            url: '/flujo-pedido',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.flujoPedido.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/flujo-pedido/flujo-pedidos.html',
                    controller: 'FlujoPedidoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('flujoPedido');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('flujo-pedido-detail', {
            parent: 'flujo-pedido',
            url: '/flujo-pedido/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.flujoPedido.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/flujo-pedido/flujo-pedido-detail.html',
                    controller: 'FlujoPedidoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('flujoPedido');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FlujoPedido', function($stateParams, FlujoPedido) {
                    return FlujoPedido.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'flujo-pedido',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('flujo-pedido-detail.edit', {
            parent: 'flujo-pedido-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/flujo-pedido/flujo-pedido-dialog.html',
                    controller: 'FlujoPedidoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FlujoPedido', function(FlujoPedido) {
                            return FlujoPedido.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('flujo-pedido.new', {
            parent: 'flujo-pedido',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/flujo-pedido/flujo-pedido-dialog.html',
                    controller: 'FlujoPedidoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                activo: false,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('flujo-pedido', null, { reload: 'flujo-pedido' });
                }, function() {
                    $state.go('flujo-pedido');
                });
            }]
        })
        .state('flujo-pedido.edit', {
            parent: 'flujo-pedido',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/flujo-pedido/flujo-pedido-dialog.html',
                    controller: 'FlujoPedidoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FlujoPedido', function(FlujoPedido) {
                            return FlujoPedido.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('flujo-pedido', null, { reload: 'flujo-pedido' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('flujo-pedido.delete', {
            parent: 'flujo-pedido',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/flujo-pedido/flujo-pedido-delete-dialog.html',
                    controller: 'FlujoPedidoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FlujoPedido', function(FlujoPedido) {
                            return FlujoPedido.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('flujo-pedido', null, { reload: 'flujo-pedido' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
