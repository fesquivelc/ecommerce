(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('AlmacenController', AlmacenController);

    AlmacenController.$inject = ['Almacen'];

    function AlmacenController(Almacen) {

        var vm = this;

        vm.almacens = [];

        loadAll();

        function loadAll() {
            Almacen.query(function(result) {
                vm.almacens = result;
                vm.searchQuery = null;
            });
        }
    }
})();
