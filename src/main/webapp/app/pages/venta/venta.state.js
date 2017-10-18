(function () {
    'use strict';

    angular
        .module('ecommerceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('ventas', {
                parent: 'pages',
                url: '/ventas',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/pages/venta/venta.html',
                        controller: 'VentasController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {

                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pedido');
                        $translatePartialLoader.addPart('estadoPedido');
                        $translatePartialLoader.addPart('formaPago');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('ventas-new', {
                parent: 'ventas',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/pages/venta/venta.edit.html',
                        controller: 'VentasEditController',
                        controllerAs: 'vm'
                    }
                }
            })
        ;
    }
})();
