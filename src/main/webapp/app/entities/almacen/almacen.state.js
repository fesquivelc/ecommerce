(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('almacen', {
            parent: 'entity',
            url: '/almacen',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.almacen.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/almacen/almacens.html',
                    controller: 'AlmacenController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('almacen');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('almacen-detail', {
            parent: 'almacen',
            url: '/almacen/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.almacen.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/almacen/almacen-detail.html',
                    controller: 'AlmacenDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('almacen');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Almacen', function($stateParams, Almacen) {
                    return Almacen.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'almacen',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('almacen-detail.edit', {
            parent: 'almacen-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/almacen/almacen-dialog.html',
                    controller: 'AlmacenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Almacen', function(Almacen) {
                            return Almacen.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('almacen.new', {
            parent: 'almacen',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/almacen/almacen-dialog.html',
                    controller: 'AlmacenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                codigo: null,
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('almacen', null, { reload: 'almacen' });
                }, function() {
                    $state.go('almacen');
                });
            }]
        })
        .state('almacen.edit', {
            parent: 'almacen',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/almacen/almacen-dialog.html',
                    controller: 'AlmacenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Almacen', function(Almacen) {
                            return Almacen.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('almacen', null, { reload: 'almacen' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('almacen.delete', {
            parent: 'almacen',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/almacen/almacen-delete-dialog.html',
                    controller: 'AlmacenDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Almacen', function(Almacen) {
                            return Almacen.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('almacen', null, { reload: 'almacen' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
