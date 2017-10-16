'use strict';

describe('Controller Tests', function() {

    describe('Direccion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDireccion, MockCliente, MockUbigeo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDireccion = jasmine.createSpy('MockDireccion');
            MockCliente = jasmine.createSpy('MockCliente');
            MockUbigeo = jasmine.createSpy('MockUbigeo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Direccion': MockDireccion,
                'Cliente': MockCliente,
                'Ubigeo': MockUbigeo
            };
            createController = function() {
                $injector.get('$controller')("DireccionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ecommerceApp:direccionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
