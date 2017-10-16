(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('InventarioController', InventarioController);

    InventarioController.$inject = ['Inventario'];

    function InventarioController(Inventario) {

        var vm = this;

        vm.inventarios = [];

        loadAll();

        function loadAll() {
            Inventario.query(function(result) {
                vm.inventarios = result;
                vm.searchQuery = null;
            });
        }
    }
})();
