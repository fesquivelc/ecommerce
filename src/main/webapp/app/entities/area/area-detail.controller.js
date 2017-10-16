(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('AreaDetailController', AreaDetailController);

    AreaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Area'];

    function AreaDetailController($scope, $rootScope, $stateParams, previousState, entity, Area) {
        var vm = this;

        vm.area = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ecommerceApp:areaUpdate', function(event, result) {
            vm.area = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
