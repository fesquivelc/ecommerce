(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('EquivalenciaController', EquivalenciaController);

    EquivalenciaController.$inject = ['Equivalencia'];

    function EquivalenciaController(Equivalencia) {

        var vm = this;

        vm.equivalencias = [];

        loadAll();

        function loadAll() {
            Equivalencia.query(function(result) {
                vm.equivalencias = result;
                vm.searchQuery = null;
            });
        }
    }
})();
