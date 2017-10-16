(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('CuotaDialogController', CuotaDialogController);

    CuotaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cuota', 'Pedido'];

    function CuotaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cuota, Pedido) {
        var vm = this;

        vm.cuota = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
            if (vm.cuota.id !== null) {
                Cuota.update(vm.cuota, onSaveSuccess, onSaveError);
            } else {
                Cuota.save(vm.cuota, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ecommerceApp:cuotaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaVencimiento = false;
        vm.datePickerOpenStatus.fechaComunicado = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
