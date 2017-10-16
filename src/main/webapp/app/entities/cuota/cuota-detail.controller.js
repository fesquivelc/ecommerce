(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('CuotaDetailController', CuotaDetailController);

    CuotaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cuota', 'Pedido'];

    function CuotaDetailController($scope, $rootScope, $stateParams, previousState, entity, Cuota, Pedido) {
        var vm = this;

        vm.cuota = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ecommerceApp:cuotaUpdate', function(event, result) {
            vm.cuota = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
