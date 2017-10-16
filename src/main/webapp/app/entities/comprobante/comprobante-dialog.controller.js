(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('ComprobanteDialogController', ComprobanteDialogController);

    ComprobanteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Comprobante', 'Pedido'];

    function ComprobanteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Comprobante, Pedido) {
        var vm = this;

        vm.comprobante = entity;
        vm.clear = clear;
        vm.save = save;
        vm.pedidos = Pedido.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.comprobante.id !== null) {
                Comprobante.update(vm.comprobante, onSaveSuccess, onSaveError);
            } else {
                Comprobante.save(vm.comprobante, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ecommerceApp:comprobanteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
