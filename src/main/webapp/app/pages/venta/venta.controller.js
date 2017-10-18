(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('VentasController', VentaController);

    VentaController.$inject = ['$scope','Pedido','Cliente','$log'];
    function VentaController($scope, Pedido, Cliente, $log) {
        var vm = this;

        $scope.cargarClientes = cargarClientes;
        $scope.cargarPedidos = cargarPedidos;
        init();

        function init(){
            $scope.cargarPedidos();
        }

        function cargarClientes(){
            Cliente.query({}, exito, error);

            function exito(data, headers){
                $scope.clienteList = data;
            }

            function error(error){
                $log.error('Error al obtener clientes: ', error);
            }
        }

        function cargarPedidos() {
            Pedido.query({}, exito, error);

            function exito(data, header) {
                $scope.ventaList = data;
            }

            function error(data) {
                $log.error('Error al cargar las ventas: ',data);
            }
        }

        activate();

        ////////////////

        function activate() { }
    }
})();
