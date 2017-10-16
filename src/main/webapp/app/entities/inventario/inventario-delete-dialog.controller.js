(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('InventarioDeleteController',InventarioDeleteController);

    InventarioDeleteController.$inject = ['$uibModalInstance', 'entity', 'Inventario'];

    function InventarioDeleteController($uibModalInstance, entity, Inventario) {
        var vm = this;

        vm.inventario = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Inventario.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
