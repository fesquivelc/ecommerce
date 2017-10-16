(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('InventarioDialogController', InventarioDialogController);

    InventarioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Inventario', 'Almacen', 'Producto'];

    function InventarioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Inventario, Almacen, Producto) {
        var vm = this;

        vm.inventario = entity;
        vm.clear = clear;
        vm.save = save;
        vm.almacens = Almacen.query();
        vm.productos = Producto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.inventario.id !== null) {
                Inventario.update(vm.inventario, onSaveSuccess, onSaveError);
            } else {
                Inventario.save(vm.inventario, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ecommerceApp:inventarioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
