'use strict';

describe('Controller Tests', function() {

    describe('Banda Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBanda, MockPais, MockGenero, MockMusico, MockAlbum, MockVotarFavoritoB, MockDiscografica;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBanda = jasmine.createSpy('MockBanda');
            MockPais = jasmine.createSpy('MockPais');
            MockGenero = jasmine.createSpy('MockGenero');
            MockMusico = jasmine.createSpy('MockMusico');
            MockAlbum = jasmine.createSpy('MockAlbum');
            MockVotarFavoritoB = jasmine.createSpy('MockVotarFavoritoB');
            MockDiscografica = jasmine.createSpy('MockDiscografica');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Banda': MockBanda,
                'Pais': MockPais,
                'Genero': MockGenero,
                'Musico': MockMusico,
                'Album': MockAlbum,
                'VotarFavoritoB': MockVotarFavoritoB,
                'Discografica': MockDiscografica
            };
            createController = function() {
                $injector.get('$controller')("BandaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appApp:bandaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
