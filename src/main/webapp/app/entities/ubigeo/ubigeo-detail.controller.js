(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('UbigeoDetailController', UbigeoDetailController);

    UbigeoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ubigeo'];

    function UbigeoDetailController($scope, $rootScope, $stateParams, previousState, entity, Ubigeo) {
        var vm = this;

        vm.ubigeo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ecommerceApp:ubigeoUpdate', function(event, result) {
            vm.ubigeo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
