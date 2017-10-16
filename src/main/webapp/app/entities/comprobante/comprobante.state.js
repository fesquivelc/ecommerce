(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('comprobante', {
            parent: 'entity',
            url: '/comprobante?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.comprobante.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comprobante/comprobantes.html',
                    controller: 'ComprobanteController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('comprobante');
                    $translatePartialLoader.addPart('tipoComprobante');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('comprobante-detail', {
            parent: 'comprobante',
            url: '/comprobante/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.comprobante.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comprobante/comprobante-detail.html',
                    controller: 'ComprobanteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('comprobante');
                    $translatePartialLoader.addPart('tipoComprobante');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Comprobante', function($stateParams, Comprobante) {
                    return Comprobante.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'comprobante',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('comprobante-detail.edit', {
            parent: 'comprobante-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comprobante/comprobante-dialog.html',
                    controller: 'ComprobanteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Comprobante', function(Comprobante) {
                            return Comprobante.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comprobante.new', {
            parent: 'comprobante',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comprobante/comprobante-dialog.html',
                    controller: 'ComprobanteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numero: null,
                                tipoComprobante: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('comprobante', null, { reload: 'comprobante' });
                }, function() {
                    $state.go('comprobante');
                });
            }]
        })
        .state('comprobante.edit', {
            parent: 'comprobante',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comprobante/comprobante-dialog.html',
                    controller: 'ComprobanteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Comprobante', function(Comprobante) {
                            return Comprobante.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comprobante', null, { reload: 'comprobante' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comprobante.delete', {
            parent: 'comprobante',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comprobante/comprobante-delete-dialog.html',
                    controller: 'ComprobanteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Comprobante', function(Comprobante) {
                            return Comprobante.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comprobante', null, { reload: 'comprobante' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
