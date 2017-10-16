(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('unidad-medida', {
            parent: 'entity',
            url: '/unidad-medida',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.unidadMedida.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/unidad-medida/unidad-medidas.html',
                    controller: 'UnidadMedidaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('unidadMedida');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('unidad-medida-detail', {
            parent: 'unidad-medida',
            url: '/unidad-medida/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.unidadMedida.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/unidad-medida/unidad-medida-detail.html',
                    controller: 'UnidadMedidaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('unidadMedida');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UnidadMedida', function($stateParams, UnidadMedida) {
                    return UnidadMedida.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'unidad-medida',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('unidad-medida-detail.edit', {
            parent: 'unidad-medida-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/unidad-medida/unidad-medida-dialog.html',
                    controller: 'UnidadMedidaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UnidadMedida', function(UnidadMedida) {
                            return UnidadMedida.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('unidad-medida.new', {
            parent: 'unidad-medida',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/unidad-medida/unidad-medida-dialog.html',
                    controller: 'UnidadMedidaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                codigo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('unidad-medida', null, { reload: 'unidad-medida' });
                }, function() {
                    $state.go('unidad-medida');
                });
            }]
        })
        .state('unidad-medida.edit', {
            parent: 'unidad-medida',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/unidad-medida/unidad-medida-dialog.html',
                    controller: 'UnidadMedidaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UnidadMedida', function(UnidadMedida) {
                            return UnidadMedida.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('unidad-medida', null, { reload: 'unidad-medida' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('unidad-medida.delete', {
            parent: 'unidad-medida',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/unidad-medida/unidad-medida-delete-dialog.html',
                    controller: 'UnidadMedidaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UnidadMedida', function(UnidadMedida) {
                            return UnidadMedida.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('unidad-medida', null, { reload: 'unidad-medida' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
