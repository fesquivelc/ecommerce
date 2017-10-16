(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('DetallePedidoDetailController', DetallePedidoDetailController);

    DetallePedidoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DetallePedido', 'UnidadMedida', 'Producto'];

    function DetallePedidoDetailController($scope, $rootScope, $stateParams, previousState, entity, DetallePedido, UnidadMedida, Producto) {
        var vm = this;

        vm.detallePedido = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ecommerceApp:detallePedidoUpdate', function(event, result) {
            vm.detallePedido = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
