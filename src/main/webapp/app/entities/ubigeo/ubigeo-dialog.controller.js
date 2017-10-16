(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('UbigeoDialogController', UbigeoDialogController);

    UbigeoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ubigeo'];

    function UbigeoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Ubigeo) {
        var vm = this;

        vm.ubigeo = entity;
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
            if (vm.ubigeo.id !== null) {
                Ubigeo.update(vm.ubigeo, onSaveSuccess, onSaveError);
            } else {
                Ubigeo.save(vm.ubigeo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ecommerceApp:ubigeoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
