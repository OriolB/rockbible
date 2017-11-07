'use strict';

describe('Controller Tests', function() {

    describe('UserExt Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserExt, MockUser, MockVotarFavoritoM, MockVotarFavoritoB, MockVotarFavoritoA, MockVotarFavoritoC;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserExt = jasmine.createSpy('MockUserExt');
            MockUser = jasmine.createSpy('MockUser');
            MockVotarFavoritoM = jasmine.createSpy('MockVotarFavoritoM');
            MockVotarFavoritoB = jasmine.createSpy('MockVotarFavoritoB');
            MockVotarFavoritoA = jasmine.createSpy('MockVotarFavoritoA');
            MockVotarFavoritoC = jasmine.createSpy('MockVotarFavoritoC');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UserExt': MockUserExt,
                'User': MockUser,
                'VotarFavoritoM': MockVotarFavoritoM,
                'VotarFavoritoB': MockVotarFavoritoB,
                'VotarFavoritoA': MockVotarFavoritoA,
                'VotarFavoritoC': MockVotarFavoritoC
            };
            createController = function() {
                $injector.get('$controller')("UserExtDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appApp:userExtUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
