'use strict';

describe('Controller Tests', function() {

    describe('Producto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProducto, MockUnidadMedida, MockCategoria, MockMarca;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProducto = jasmine.createSpy('MockProducto');
            MockUnidadMedida = jasmine.createSpy('MockUnidadMedida');
            MockCategoria = jasmine.createSpy('MockCategoria');
            MockMarca = jasmine.createSpy('MockMarca');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Producto': MockProducto,
                'UnidadMedida': MockUnidadMedida,
                'Categoria': MockCategoria,
                'Marca': MockMarca
            };
            createController = function() {
                $injector.get('$controller')("ProductoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ecommerceApp:productoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
