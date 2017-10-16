(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('personal', {
            parent: 'entity',
            url: '/personal?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.personal.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/personal/personals.html',
                    controller: 'PersonalController',
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
                    $translatePartialLoader.addPart('personal');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('personal-detail', {
            parent: 'personal',
            url: '/personal/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ecommerceApp.personal.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/personal/personal-detail.html',
                    controller: 'PersonalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personal');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Personal', function($stateParams, Personal) {
                    return Personal.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'personal',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('personal-detail.edit', {
            parent: 'personal-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personal/personal-dialog.html',
                    controller: 'PersonalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Personal', function(Personal) {
                            return Personal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('personal.new', {
            parent: 'personal',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personal/personal-dialog.html',
                    controller: 'PersonalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                paterno: null,
                                materno: null,
                                nombres: null,
                                numeroDocumento: null,
                                email: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('personal', null, { reload: 'personal' });
                }, function() {
                    $state.go('personal');
                });
            }]
        })
        .state('personal.edit', {
            parent: 'personal',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personal/personal-dialog.html',
                    controller: 'PersonalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Personal', function(Personal) {
                            return Personal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('personal', null, { reload: 'personal' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('personal.delete', {
            parent: 'personal',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personal/personal-delete-dialog.html',
                    controller: 'PersonalDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Personal', function(Personal) {
                            return Personal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('personal', null, { reload: 'personal' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
