(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('ComprobanteDetailController', ComprobanteDetailController);

    ComprobanteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Comprobante', 'Pedido'];

    function ComprobanteDetailController($scope, $rootScope, $stateParams, previousState, entity, Comprobante, Pedido) {
        var vm = this;

        vm.comprobante = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ecommerceApp:comprobanteUpdate', function(event, result) {
            vm.comprobante = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
