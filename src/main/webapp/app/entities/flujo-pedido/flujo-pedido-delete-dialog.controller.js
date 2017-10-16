(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('FlujoPedidoDeleteController',FlujoPedidoDeleteController);

    FlujoPedidoDeleteController.$inject = ['$uibModalInstance', 'entity', 'FlujoPedido'];

    function FlujoPedidoDeleteController($uibModalInstance, entity, FlujoPedido) {
        var vm = this;

        vm.flujoPedido = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FlujoPedido.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
