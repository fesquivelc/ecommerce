(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('DireccionController', DireccionController);

    DireccionController.$inject = ['Direccion'];

    function DireccionController(Direccion) {

        var vm = this;

        vm.direccions = [];

        loadAll();

        function loadAll() {
            Direccion.query(function(result) {
                vm.direccions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
