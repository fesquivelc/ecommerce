(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('AreaDeleteController',AreaDeleteController);

    AreaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Area'];

    function AreaDeleteController($uibModalInstance, entity, Area) {
        var vm = this;

        vm.area = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Area.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
