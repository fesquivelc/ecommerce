(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('FlujoPedidoDetailController', FlujoPedidoDetailController);

    FlujoPedidoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FlujoPedido', 'Cargo'];

    function FlujoPedidoDetailController($scope, $rootScope, $stateParams, previousState, entity, FlujoPedido, Cargo) {
        var vm = this;

        vm.flujoPedido = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ecommerceApp:flujoPedidoUpdate', function(event, result) {
            vm.flujoPedido = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
