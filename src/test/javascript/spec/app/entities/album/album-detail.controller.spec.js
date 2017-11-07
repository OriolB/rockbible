'use strict';

describe('Controller Tests', function() {

    describe('Album Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAlbum, MockBanda, MockMusico, MockGenero, MockVotarFavoritoA, MockCancion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAlbum = jasmine.createSpy('MockAlbum');
            MockBanda = jasmine.createSpy('MockBanda');
            MockMusico = jasmine.createSpy('MockMusico');
            MockGenero = jasmine.createSpy('MockGenero');
            MockVotarFavoritoA = jasmine.createSpy('MockVotarFavoritoA');
            MockCancion = jasmine.createSpy('MockCancion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Album': MockAlbum,
                'Banda': MockBanda,
                'Musico': MockMusico,
                'Genero': MockGenero,
                'VotarFavoritoA': MockVotarFavoritoA,
                'Cancion': MockCancion
            };
            createController = function() {
                $injector.get('$controller')("AlbumDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appApp:albumUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
