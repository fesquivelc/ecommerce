(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('PedidoDetailController', PedidoDetailController);

    PedidoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pedido', 'Cliente', 'Personal'];

    function PedidoDetailController($scope, $rootScope, $stateParams, previousState, entity, Pedido, Cliente, Personal) {
        var vm = this;

        vm.pedido = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ecommerceApp:pedidoUpdate', function(event, result) {
            vm.pedido = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
