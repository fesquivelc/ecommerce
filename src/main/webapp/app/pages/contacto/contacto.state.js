(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('contactos', {
                parent: 'pages',
                url: '/contactos',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/pages/contacto/contacto.html',
                        controller: 'ContactosController',
                        controllerAs: 'vm'
                    }
                }
            })
            .state('contactos-new', {
                parent: 'contactos',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/pages/contacto/contacto.edit.html',
                        controller: 'ContactosEditController',
                        controllerAs: 'vm'
                    }
                }
            });
    }
})();