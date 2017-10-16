(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('UbigeoDeleteController',UbigeoDeleteController);

    UbigeoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ubigeo'];

    function UbigeoDeleteController($uibModalInstance, entity, Ubigeo) {
        var vm = this;

        vm.ubigeo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Ubigeo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
