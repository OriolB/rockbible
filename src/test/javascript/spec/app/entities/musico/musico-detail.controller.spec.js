'use strict';

describe('Controller Tests', function() {

    describe('Musico Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMusico, MockBanda, MockPais, MockAlbum, MockVotarFavoritoM;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMusico = jasmine.createSpy('MockMusico');
            MockBanda = jasmine.createSpy('MockBanda');
            MockPais = jasmine.createSpy('MockPais');
            MockAlbum = jasmine.createSpy('MockAlbum');
            MockVotarFavoritoM = jasmine.createSpy('MockVotarFavoritoM');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Musico': MockMusico,
                'Banda': MockBanda,
                'Pais': MockPais,
                'Album': MockAlbum,
                'VotarFavoritoM': MockVotarFavoritoM
            };
            createController = function() {
                $injector.get('$controller')("MusicoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appApp:musicoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
