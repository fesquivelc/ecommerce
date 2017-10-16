(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('EquivalenciaDialogController', EquivalenciaDialogController);

    EquivalenciaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Equivalencia', 'UnidadMedida'];

    function EquivalenciaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Equivalencia, UnidadMedida) {
        var vm = this;

        vm.equivalencia = entity;
        vm.clear = clear;
        vm.save = save;
        vm.unidadmedidas = UnidadMedida.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.equivalencia.id !== null) {
                Equivalencia.update(vm.equivalencia, onSaveSuccess, onSaveError);
            } else {
                Equivalencia.save(vm.equivalencia, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ecommerceApp:equivalenciaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
