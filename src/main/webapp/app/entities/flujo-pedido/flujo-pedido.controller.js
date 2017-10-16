(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('FlujoPedidoController', FlujoPedidoController);

    FlujoPedidoController.$inject = ['FlujoPedido'];

    function FlujoPedidoController(FlujoPedido) {

        var vm = this;

        vm.flujoPedidos = [];

        loadAll();

        function loadAll() {
            FlujoPedido.query(function(result) {
                vm.flujoPedidos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
