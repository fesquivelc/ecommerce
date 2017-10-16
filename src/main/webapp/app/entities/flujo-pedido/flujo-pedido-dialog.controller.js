(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('FlujoPedidoDialogController', FlujoPedidoDialogController);

    FlujoPedidoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FlujoPedido', 'Cargo'];

    function FlujoPedidoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FlujoPedido, Cargo) {
        var vm = this;

        vm.flujoPedido = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cargos = Cargo.query();
        vm.flujopedidos = FlujoPedido.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.flujoPedido.id !== null) {
                FlujoPedido.update(vm.flujoPedido, onSaveSuccess, onSaveError);
            } else {
                FlujoPedido.save(vm.flujoPedido, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ecommerceApp:flujoPedidoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
