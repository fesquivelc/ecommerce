(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('ComprobanteDeleteController',ComprobanteDeleteController);

    ComprobanteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Comprobante'];

    function ComprobanteDeleteController($uibModalInstance, entity, Comprobante) {
        var vm = this;

        vm.comprobante = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Comprobante.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
