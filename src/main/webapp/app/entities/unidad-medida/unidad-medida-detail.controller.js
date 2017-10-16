(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('UnidadMedidaDetailController', UnidadMedidaDetailController);

    UnidadMedidaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UnidadMedida'];

    function UnidadMedidaDetailController($scope, $rootScope, $stateParams, previousState, entity, UnidadMedida) {
        var vm = this;

        vm.unidadMedida = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ecommerceApp:unidadMedidaUpdate', function(event, result) {
            vm.unidadMedida = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
