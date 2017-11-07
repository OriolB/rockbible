'use strict';

describe('Controller Tests', function() {

    describe('VotarFavoritoA Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockVotarFavoritoA, MockUserExt, MockAlbum;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockVotarFavoritoA = jasmine.createSpy('MockVotarFavoritoA');
            MockUserExt = jasmine.createSpy('MockUserExt');
            MockAlbum = jasmine.createSpy('MockAlbum');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'VotarFavoritoA': MockVotarFavoritoA,
                'UserExt': MockUserExt,
                'Album': MockAlbum
            };
            createController = function() {
                $injector.get('$controller')("VotarFavoritoADetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appApp:votarFavoritoAUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
