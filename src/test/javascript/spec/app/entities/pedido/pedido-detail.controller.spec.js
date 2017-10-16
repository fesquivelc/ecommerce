'use strict';

describe('Controller Tests', function() {

    describe('Pedido Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPedido, MockCliente, MockPersonal;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPedido = jasmine.createSpy('MockPedido');
            MockCliente = jasmine.createSpy('MockCliente');
            MockPersonal = jasmine.createSpy('MockPersonal');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Pedido': MockPedido,
                'Cliente': MockCliente,
                'Personal': MockPersonal
            };
            createController = function() {
                $injector.get('$controller')("PedidoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ecommerceApp:pedidoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
