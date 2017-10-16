(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('pedido', {
            parent: 'pages',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/pages/venta/venta.html',
                    controller: 'PedidoController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
