(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cuota', {
            parent: 'entity',
            url: '/cuota',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.cuota.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cuota/cuotas.html',
                    controller: 'CuotaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cuota');
                    $translatePartialLoader.addPart('cuotaEstado');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cuota-detail', {
            parent: 'cuota',
            url: '/cuota/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.cuota.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cuota/cuota-detail.html',
                    controller: 'CuotaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cuota');
                    $translatePartialLoader.addPart('cuotaEstado');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cuota', function($stateParams, Cuota) {
                    return Cuota.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cuota',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cuota-detail.edit', {
            parent: 'cuota-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cuota/cuota-dialog.html',
                    controller: 'CuotaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cuota', function(Cuota) {
                            return Cuota.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cuota.new', {
            parent: 'cuota',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cuota/cuota-dialog.html',
                    controller: 'CuotaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fechaVencimiento: null,
                                fechaComunicado: null,
                                monto: null,
                                estado: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cuota', null, { reload: 'cuota' });
                }, function() {
                    $state.go('cuota');
                });
            }]
        })
        .state('cuota.edit', {
            parent: 'cuota',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cuota/cuota-dialog.html',
                    controller: 'CuotaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cuota', function(Cuota) {
                            return Cuota.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cuota', null, { reload: 'cuota' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cuota.delete', {
            parent: 'cuota',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cuota/cuota-delete-dialog.html',
                    controller: 'CuotaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cuota', function(Cuota) {
                            return Cuota.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cuota', null, { reload: 'cuota' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
