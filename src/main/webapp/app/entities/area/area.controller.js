(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('AreaController', AreaController);

    AreaController.$inject = ['Area'];

    function AreaController(Area) {

        var vm = this;

        vm.areas = [];

        loadAll();

        function loadAll() {
            Area.query(function(result) {
                vm.areas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
