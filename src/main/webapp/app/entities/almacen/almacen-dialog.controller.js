(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('AlmacenDialogController', AlmacenDialogController);

    AlmacenDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Almacen', 'Personal'];

    function AlmacenDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Almacen, Personal) {
        var vm = this;

        vm.almacen = entity;
        vm.clear = clear;
        vm.save = save;
        vm.personals = Personal.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.almacen.id !== null) {
                Almacen.update(vm.almacen, onSaveSuccess, onSaveError);
            } else {
                Almacen.save(vm.almacen, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ecommerceApp:almacenUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
