(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('AlmacenDeleteController',AlmacenDeleteController);

    AlmacenDeleteController.$inject = ['$uibModalInstance', 'entity', 'Almacen'];

    function AlmacenDeleteController($uibModalInstance, entity, Almacen) {
        var vm = this;

        vm.almacen = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Almacen.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
