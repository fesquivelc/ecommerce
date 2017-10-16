(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('marca', {
            parent: 'entity',
            url: '/marca',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.marca.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/marca/marcas.html',
                    controller: 'MarcaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('marca');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('marca-detail', {
            parent: 'marca',
            url: '/marca/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.marca.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/marca/marca-detail.html',
                    controller: 'MarcaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('marca');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Marca', function($stateParams, Marca) {
                    return Marca.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'marca',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('marca-detail.edit', {
            parent: 'marca-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/marca/marca-dialog.html',
                    controller: 'MarcaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Marca', function(Marca) {
                            return Marca.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('marca.new', {
            parent: 'marca',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/marca/marca-dialog.html',
                    controller: 'MarcaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('marca', null, { reload: 'marca' });
                }, function() {
                    $state.go('marca');
                });
            }]
        })
        .state('marca.edit', {
            parent: 'marca',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/marca/marca-dialog.html',
                    controller: 'MarcaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Marca', function(Marca) {
                            return Marca.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('marca', null, { reload: 'marca' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('marca.delete', {
            parent: 'marca',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/marca/marca-delete-dialog.html',
                    controller: 'MarcaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Marca', function(Marca) {
                            return Marca.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('marca', null, { reload: 'marca' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
