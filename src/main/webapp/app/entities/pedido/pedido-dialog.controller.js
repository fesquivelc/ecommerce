(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('PedidoDialogController', PedidoDialogController);

    PedidoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pedido', 'Cliente', 'Personal'];

    function PedidoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Pedido, Cliente, Personal) {
        var vm = this;

        vm.pedido = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.clientes = Cliente.query();
        vm.personals = Personal.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pedido.id !== null) {
                Pedido.update(vm.pedido, onSaveSuccess, onSaveError);
            } else {
                Pedido.save(vm.pedido, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ecommerceApp:pedidoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fecha = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
