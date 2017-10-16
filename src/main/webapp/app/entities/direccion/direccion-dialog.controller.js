(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('DireccionDialogController', DireccionDialogController);

    DireccionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Direccion', 'Cliente', 'Ubigeo'];

    function DireccionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Direccion, Cliente, Ubigeo) {
        var vm = this;

        vm.direccion = entity;
        vm.clear = clear;
        vm.save = save;
        vm.clientes = Cliente.query();
        vm.ubigeos = Ubigeo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.direccion.id !== null) {
                Direccion.update(vm.direccion, onSaveSuccess, onSaveError);
            } else {
                Direccion.save(vm.direccion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ecommerceApp:direccionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
