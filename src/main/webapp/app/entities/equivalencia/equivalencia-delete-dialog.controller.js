(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('EquivalenciaDeleteController',EquivalenciaDeleteController);

    EquivalenciaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Equivalencia'];

    function EquivalenciaDeleteController($uibModalInstance, entity, Equivalencia) {
        var vm = this;

        vm.equivalencia = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Equivalencia.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
