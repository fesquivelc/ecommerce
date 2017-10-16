(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('UnidadMedidaDeleteController',UnidadMedidaDeleteController);

    UnidadMedidaDeleteController.$inject = ['$uibModalInstance', 'entity', 'UnidadMedida'];

    function UnidadMedidaDeleteController($uibModalInstance, entity, UnidadMedida) {
        var vm = this;

        vm.unidadMedida = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UnidadMedida.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
