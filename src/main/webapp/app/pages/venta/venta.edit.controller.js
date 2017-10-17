(function() {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('VentasEditController', VentaEditController);

    VentaEditController.$inject = ['$scope', 'Pedido', 'Cliente', '$log', '$stateParams'];

    function VentaEditController($scope, Pedido, Cliente, $log, $stateParams) {
        var vm = this;

        $scope.cargarClientes = cargarClientes;
        $scope.cargarPU = cargarPU;
        $scope.agregarProducto = agregarProducto;
        init();

        function init() {
            $scope.pedido = {
                cliente: null,
                fecha_bp: new Date(),
                formaPago: 'CONTADO',
                detallePedidoList: []
            }
            if ($stateParams.cliente !== null) {

            }

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

        function cargarPU() {

        }

        function agregarProducto() {

        }

        activate();

        ////////////////

        function activate() {}
    }
})();