(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('VotarFavoritoBDetailController', VotarFavoritoBDetailController);

    VotarFavoritoBDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'VotarFavoritoB', 'UserExt', 'Banda'];

    function VotarFavoritoBDetailController($scope, $rootScope, $stateParams, previousState, entity, VotarFavoritoB, UserExt, Banda) {
        var vm = this;

        vm.votarFavoritoB = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appApp:votarFavoritoBUpdate', function(event, result) {
            vm.votarFavoritoB = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
