(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('MarcaController', MarcaController);

    MarcaController.$inject = ['Marca'];

    function MarcaController(Marca) {

        var vm = this;

        vm.marcas = [];

        loadAll();

        function loadAll() {
            Marca.query(function(result) {
                vm.marcas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
