(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('equivalencia', {
            parent: 'entity',
            url: '/equivalencia',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.equivalencia.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/equivalencia/equivalencias.html',
                    controller: 'EquivalenciaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('equivalencia');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('equivalencia-detail', {
            parent: 'equivalencia',
            url: '/equivalencia/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.equivalencia.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/equivalencia/equivalencia-detail.html',
                    controller: 'EquivalenciaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('equivalencia');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Equivalencia', function($stateParams, Equivalencia) {
                    return Equivalencia.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'equivalencia',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('equivalencia-detail.edit', {
            parent: 'equivalencia-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/equivalencia/equivalencia-dialog.html',
                    controller: 'EquivalenciaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Equivalencia', function(Equivalencia) {
                            return Equivalencia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('equivalencia.new', {
            parent: 'equivalencia',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/equivalencia/equivalencia-dialog.html',
                    controller: 'EquivalenciaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                valor: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('equivalencia', null, { reload: 'equivalencia' });
                }, function() {
                    $state.go('equivalencia');
                });
            }]
        })
        .state('equivalencia.edit', {
            parent: 'equivalencia',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/equivalencia/equivalencia-dialog.html',
                    controller: 'EquivalenciaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Equivalencia', function(Equivalencia) {
                            return Equivalencia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('equivalencia', null, { reload: 'equivalencia' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('equivalencia.delete', {
            parent: 'equivalencia',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/equivalencia/equivalencia-delete-dialog.html',
                    controller: 'EquivalenciaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Equivalencia', function(Equivalencia) {
                            return Equivalencia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('equivalencia', null, { reload: 'equivalencia' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
