(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('GeneroDetailController', GeneroDetailController);

    GeneroDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Genero', 'Banda', 'Album'];

    function GeneroDetailController($scope, $rootScope, $stateParams, previousState, entity, Genero, Banda, Album) {
        var vm = this;

        vm.genero = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appApp:generoUpdate', function(event, result) {
            vm.genero = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
