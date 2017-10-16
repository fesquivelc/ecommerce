(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('UbigeoController', UbigeoController);

    UbigeoController.$inject = ['Ubigeo'];

    function UbigeoController(Ubigeo) {

        var vm = this;

        vm.ubigeos = [];

        loadAll();

        function loadAll() {
            Ubigeo.query(function(result) {
                vm.ubigeos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
