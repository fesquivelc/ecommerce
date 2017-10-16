(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ubigeo', {
            parent: 'entity',
            url: '/ubigeo',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.ubigeo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ubigeo/ubigeos.html',
                    controller: 'UbigeoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ubigeo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ubigeo-detail', {
            parent: 'ubigeo',
            url: '/ubigeo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.ubigeo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ubigeo/ubigeo-detail.html',
                    controller: 'UbigeoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ubigeo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Ubigeo', function($stateParams, Ubigeo) {
                    return Ubigeo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ubigeo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ubigeo-detail.edit', {
            parent: 'ubigeo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ubigeo/ubigeo-dialog.html',
                    controller: 'UbigeoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ubigeo', function(Ubigeo) {
                            return Ubigeo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ubigeo.new', {
            parent: 'ubigeo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ubigeo/ubigeo-dialog.html',
                    controller: 'UbigeoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                codigo: null,
                                departamento: null,
                                provincia: null,
                                distrito: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ubigeo', null, { reload: 'ubigeo' });
                }, function() {
                    $state.go('ubigeo');
                });
            }]
        })
        .state('ubigeo.edit', {
            parent: 'ubigeo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ubigeo/ubigeo-dialog.html',
                    controller: 'UbigeoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ubigeo', function(Ubigeo) {
                            return Ubigeo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ubigeo', null, { reload: 'ubigeo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ubigeo.delete', {
            parent: 'ubigeo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ubigeo/ubigeo-delete-dialog.html',
                    controller: 'UbigeoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ubigeo', function(Ubigeo) {
                            return Ubigeo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ubigeo', null, { reload: 'ubigeo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
