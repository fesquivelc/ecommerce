(function () {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('VentasEditController', VentaEditController);

    VentaEditController.$inject = ['$scope', 'Pedido', 'Cliente', '$log'];

    function VentaEditController($scope, Pedido, Cliente, $log) {
        var vm = this;

        $scope.cargarClientes = cargarClientes;
        init();

        function init() {
            $scope.cargarClientes();
        }

        function cargarClientes() {
            Cliente.query({}, exito, error);

            function exito(data, headers) {
                $scope.clienteList = data;
            }

            function error(error) {
                $log.error('Error al obtener clientes: ', error);
            }
        }

        activate();

        ////////////////

        function activate() {
        }
    }
})();
