(function () {
    'use strict';

    angular
        .module('ecommerceApp')
        .controller('VentasEditController', VentaEditController);

    VentaEditController.$inject = ['$state', '$scope', 'Pedido', 'Cliente', 'Producto', '$log', '$stateParams'];

    function VentaEditController($state, $scope, Pedido, Cliente, Producto, $log, $stateParams) {
        var vm = this;

        $scope.cargarClientes = cargarClientes;
        $scope.cargarPU = cargarPU;
        $scope.agregarProducto = agregarProducto;
        $scope.cargarProductos = cargarProductos;
        $scope.guardar = guardar;
        $scope.eliminar = eliminar;

        init();

        function guardar() {
            vm.isSaving = true;
            Pedido.save($scope.pedido, exito, error);

            function exito(result) {
                $scope.$emit('ecommerceApp:pedidoUpdate', result);
                vm.isSaving = false;
                $state.go('ventas', {}, {reload: true});
            }

            function error() {

            }
        }

        function init() {
            $scope.pedido = {
                cliente: null,
                fecha_bp: new Date(),
                formaPago: 'CONTADO',
                detallePedidoList: [],
                montoVenta : 0.0
            };

            $scope.cargarClientes();
            $scope.cargarProductos();
            vm.isSaving = false;
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

        function cargarProductos() {
            Producto.query({}, exito, error);

            function exito(data, headers) {
                $scope.productoList = data;
            }

            function error(data) {
                $log.error('Error al obtener productos', data);
            }
        }

        function cargarPU(detalle) {
            detalle.precioUnitario = detalle.producto.precioBase;
            detalle.unidadMedida = detalle.producto.unidadMedida;
        }

        function agregarProducto() {
            $scope.pedido.detallePedidoList.push(
                {
                    producto: null,
                    cantidad: 1,
                    unidadMedida: null,
                    precioUnitario: 0.0,
                    montoVenta: 0.0
                }
            );
        }

        function eliminar(index) {
            var montoDetalle = $scope.pedido.detallePedidoList[index].precioUnitario * $scope.pedido.detallePedidoList[index].cantidad;
            $scope.pedido.montoVenta =  $scope.pedido.montoVenta - montoDetalle;
            $scope.pedido.detallePedidoList.splice(index, 1);
        }

        activate();

        ////////////////

        function activate() {
        }
    }
})();
