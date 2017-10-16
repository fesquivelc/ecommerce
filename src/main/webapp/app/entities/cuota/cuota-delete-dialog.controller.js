(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('CuotaDeleteController',CuotaDeleteController);

    CuotaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cuota'];

    function CuotaDeleteController($uibModalInstance, entity, Cuota) {
        var vm = this;

        vm.cuota = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Cuota.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
