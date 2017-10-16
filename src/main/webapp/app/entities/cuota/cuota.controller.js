(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('CuotaController', CuotaController);

    CuotaController.$inject = ['Cuota'];

    function CuotaController(Cuota) {

        var vm = this;

        vm.cuotas = [];

        loadAll();

        function loadAll() {
            Cuota.query(function(result) {
                vm.cuotas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
