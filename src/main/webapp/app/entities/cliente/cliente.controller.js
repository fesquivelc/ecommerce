(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('ClienteController', ClienteController);

    ClienteController.$inject = ['Cliente'];

    function ClienteController(Cliente) {

        var vm = this;

        vm.clientes = [];

        loadAll();

        function loadAll() {
            Cliente.query(function(result) {
                vm.clientes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
