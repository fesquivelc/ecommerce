(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('InventarioDetailController', InventarioDetailController);

    InventarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Inventario', 'Almacen', 'Producto'];

    function InventarioDetailController($scope, $rootScope, $stateParams, previousState, entity, Inventario, Almacen, Producto) {
        var vm = this;

        vm.inventario = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ecommerceApp:inventarioUpdate', function(event, result) {
            vm.inventario = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
