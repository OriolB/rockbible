'use strict';

describe('Controller Tests', function() {

    describe('Pais Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPais, MockCiudad, MockMusico, MockBanda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPais = jasmine.createSpy('MockPais');
            MockCiudad = jasmine.createSpy('MockCiudad');
            MockMusico = jasmine.createSpy('MockMusico');
            MockBanda = jasmine.createSpy('MockBanda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Pais': MockPais,
                'Ciudad': MockCiudad,
                'Musico': MockMusico,
                'Banda': MockBanda
            };
            createController = function() {
                $injector.get('$controller')("PaisDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appApp:paisUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
