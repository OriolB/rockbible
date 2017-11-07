(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('VotarFavoritoCDetailController', VotarFavoritoCDetailController);

    VotarFavoritoCDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'VotarFavoritoC', 'UserExt', 'Cancion'];

    function VotarFavoritoCDetailController($scope, $rootScope, $stateParams, previousState, entity, VotarFavoritoC, UserExt, Cancion) {
        var vm = this;

        vm.votarFavoritoC = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appApp:votarFavoritoCUpdate', function(event, result) {
            vm.votarFavoritoC = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
