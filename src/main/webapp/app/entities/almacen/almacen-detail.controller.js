(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('AlmacenDetailController', AlmacenDetailController);

    AlmacenDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Almacen', 'Personal'];

    function AlmacenDetailController($scope, $rootScope, $stateParams, previousState, entity, Almacen, Personal) {
        var vm = this;

        vm.almacen = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ecommerceApp:almacenUpdate', function(event, result) {
            vm.almacen = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
