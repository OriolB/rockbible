'use strict';

describe('Controller Tests', function() {

    describe('VotarFavoritoC Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockVotarFavoritoC, MockUserExt, MockCancion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockVotarFavoritoC = jasmine.createSpy('MockVotarFavoritoC');
            MockUserExt = jasmine.createSpy('MockUserExt');
            MockCancion = jasmine.createSpy('MockCancion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'VotarFavoritoC': MockVotarFavoritoC,
                'UserExt': MockUserExt,
                'Cancion': MockCancion
            };
            createController = function() {
                $injector.get('$controller')("VotarFavoritoCDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appApp:votarFavoritoCUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
