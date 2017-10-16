(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('DetallePedidoDialogController', DetallePedidoDialogController);

    DetallePedidoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DetallePedido', 'UnidadMedida', 'Producto'];

    function DetallePedidoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DetallePedido, UnidadMedida, Producto) {
        var vm = this;

        vm.detallePedido = entity;
        vm.clear = clear;
        vm.save = save;
        vm.unidadmedidas = UnidadMedida.query();
        vm.productos = Producto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.detallePedido.id !== null) {
                DetallePedido.update(vm.detallePedido, onSaveSuccess, onSaveError);
            } else {
                DetallePedido.save(vm.detallePedido, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ecommerceApp:detallePedidoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
