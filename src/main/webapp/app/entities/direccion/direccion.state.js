(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('direccion', {
            parent: 'entity',
            url: '/direccion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.direccion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/direccion/direccions.html',
                    controller: 'DireccionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('direccion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('direccion-detail', {
            parent: 'direccion',
            url: '/direccion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.direccion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/direccion/direccion-detail.html',
                    controller: 'DireccionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('direccion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Direccion', function($stateParams, Direccion) {
                    return Direccion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'direccion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('direccion-detail.edit', {
            parent: 'direccion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/direccion/direccion-dialog.html',
                    controller: 'DireccionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Direccion', function(Direccion) {
                            return Direccion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('direccion.new', {
            parent: 'direccion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/direccion/direccion-dialog.html',
                    controller: 'DireccionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                direccion: null,
                                numero: null,
                                telefono: null,
                                celular: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('direccion', null, { reload: 'direccion' });
                }, function() {
                    $state.go('direccion');
                });
            }]
        })
        .state('direccion.edit', {
            parent: 'direccion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/direccion/direccion-dialog.html',
                    controller: 'DireccionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Direccion', function(Direccion) {
                            return Direccion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('direccion', null, { reload: 'direccion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('direccion.delete', {
            parent: 'direccion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/direccion/direccion-delete-dialog.html',
                    controller: 'DireccionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Direccion', function(Direccion) {
                            return Direccion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('direccion', null, { reload: 'direccion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
