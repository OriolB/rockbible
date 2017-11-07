(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('VotarFavoritoADetailController', VotarFavoritoADetailController);

    VotarFavoritoADetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'VotarFavoritoA', 'UserExt', 'Album'];

    function VotarFavoritoADetailController($scope, $rootScope, $stateParams, previousState, entity, VotarFavoritoA, UserExt, Album) {
        var vm = this;

        vm.votarFavoritoA = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appApp:votarFavoritoAUpdate', function(event, result) {
            vm.votarFavoritoA = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
