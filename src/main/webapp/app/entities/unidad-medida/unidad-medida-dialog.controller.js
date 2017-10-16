(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('UnidadMedidaDialogController', UnidadMedidaDialogController);

    UnidadMedidaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UnidadMedida'];

    function UnidadMedidaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UnidadMedida) {
        var vm = this;

        vm.unidadMedida = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.unidadMedida.id !== null) {
                UnidadMedida.update(vm.unidadMedida, onSaveSuccess, onSaveError);
            } else {
                UnidadMedida.save(vm.unidadMedida, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ecommerceApp:unidadMedidaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
