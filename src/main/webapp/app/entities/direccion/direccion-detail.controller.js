(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('DireccionDetailController', DireccionDetailController);

    DireccionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Direccion', 'Cliente', 'Ubigeo'];

    function DireccionDetailController($scope, $rootScope, $stateParams, previousState, entity, Direccion, Cliente, Ubigeo) {
        var vm = this;

        vm.direccion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ecommerceApp:direccionUpdate', function(event, result) {
            vm.direccion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
