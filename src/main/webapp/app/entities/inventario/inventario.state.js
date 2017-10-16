(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('inventario', {
            parent: 'entity',
            url: '/inventario',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.inventario.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/inventario/inventarios.html',
                    controller: 'InventarioController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('inventario');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('inventario-detail', {
            parent: 'inventario',
            url: '/inventario/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.inventario.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/inventario/inventario-detail.html',
                    controller: 'InventarioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('inventario');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Inventario', function($stateParams, Inventario) {
                    return Inventario.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'inventario',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('inventario-detail.edit', {
            parent: 'inventario-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inventario/inventario-dialog.html',
                    controller: 'InventarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Inventario', function(Inventario) {
                            return Inventario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('inventario.new', {
            parent: 'inventario',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inventario/inventario-dialog.html',
                    controller: 'InventarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                stock: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('inventario', null, { reload: 'inventario' });
                }, function() {
                    $state.go('inventario');
                });
            }]
        })
        .state('inventario.edit', {
            parent: 'inventario',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inventario/inventario-dialog.html',
                    controller: 'InventarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Inventario', function(Inventario) {
                            return Inventario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('inventario', null, { reload: 'inventario' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('inventario.delete', {
            parent: 'inventario',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inventario/inventario-delete-dialog.html',
                    controller: 'InventarioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Inventario', function(Inventario) {
                            return Inventario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('inventario', null, { reload: 'inventario' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
