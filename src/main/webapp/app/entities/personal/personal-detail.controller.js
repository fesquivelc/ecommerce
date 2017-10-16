(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('PersonalDetailController', PersonalDetailController);

    PersonalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Personal', 'Area', 'Cargo'];

    function PersonalDetailController($scope, $rootScope, $stateParams, previousState, entity, Personal, Area, Cargo) {
        var vm = this;

        vm.personal = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ecommerceApp:personalUpdate', function(event, result) {
            vm.personal = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
