(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('PersonalDialogController', PersonalDialogController);

    PersonalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Personal', 'Area', 'Cargo'];

    function PersonalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Personal, Area, Cargo) {
        var vm = this;

        vm.personal = entity;
        vm.clear = clear;
        vm.save = save;
        vm.areas = Area.query();
        vm.cargos = Cargo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personal.id !== null) {
                Personal.update(vm.personal, onSaveSuccess, onSaveError);
            } else {
                Personal.save(vm.personal, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ecommerceApp:personalUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
