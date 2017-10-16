(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('ProductoDetailController', ProductoDetailController);

    ProductoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Producto', 'UnidadMedida', 'Categoria', 'Marca'];

    function ProductoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Producto, UnidadMedida, Categoria, Marca) {
        var vm = this;

        vm.producto = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('ecommerceApp:productoUpdate', function(event, result) {
            vm.producto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
