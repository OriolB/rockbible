'use strict';

describe('Controller Tests', function() {

    describe('VotarFavoritoM Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockVotarFavoritoM, MockUserExt, MockMusico;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockVotarFavoritoM = jasmine.createSpy('MockVotarFavoritoM');
            MockUserExt = jasmine.createSpy('MockUserExt');
            MockMusico = jasmine.createSpy('MockMusico');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'VotarFavoritoM': MockVotarFavoritoM,
                'UserExt': MockUserExt,
                'Musico': MockMusico
            };
            createController = function() {
                $injector.get('$controller')("VotarFavoritoMDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appApp:votarFavoritoMUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
