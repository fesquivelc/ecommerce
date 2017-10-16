(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('ProductoDialogController', ProductoDialogController);

    ProductoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Producto', 'UnidadMedida', 'Categoria', 'Marca'];

    function ProductoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Producto, UnidadMedida, Categoria, Marca) {
        var vm = this;

        vm.producto = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.unidadmedidas = UnidadMedida.query();
        vm.categorias = Categoria.query();
        vm.marcas = Marca.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.producto.id !== null) {
                Producto.update(vm.producto, onSaveSuccess, onSaveError);
            } else {
                Producto.save(vm.producto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ecommerceApp:productoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImagen = function ($file, producto) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        producto.imagen = base64Data;
                        producto.imagenContentType = $file.type;
                    });
                });
            }
        };

    }
})();
