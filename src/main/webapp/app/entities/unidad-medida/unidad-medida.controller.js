(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('UnidadMedidaController', UnidadMedidaController);

    UnidadMedidaController.$inject = ['UnidadMedida'];

    function UnidadMedidaController(UnidadMedida) {

        var vm = this;

        vm.unidadMedidas = [];

        loadAll();

        function loadAll() {
            UnidadMedida.query(function(result) {
                vm.unidadMedidas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
