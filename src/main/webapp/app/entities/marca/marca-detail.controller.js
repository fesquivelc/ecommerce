(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('MarcaDetailController', MarcaDetailController);

    MarcaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Marca'];

    function MarcaDetailController($scope, $rootScope, $stateParams, previousState, entity, Marca) {
        var vm = this;

        vm.marca = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ecommerceApp:marcaUpdate', function(event, result) {
            vm.marca = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
