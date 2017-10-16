(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('EquivalenciaDetailController', EquivalenciaDetailController);

    EquivalenciaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Equivalencia', 'UnidadMedida'];

    function EquivalenciaDetailController($scope, $rootScope, $stateParams, previousState, entity, Equivalencia, UnidadMedida) {
        var vm = this;

        vm.equivalencia = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ecommerceApp:equivalenciaUpdate', function(event, result) {
            vm.equivalencia = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
