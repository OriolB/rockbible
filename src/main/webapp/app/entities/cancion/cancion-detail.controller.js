(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('CancionDetailController', CancionDetailController);

    CancionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cancion', 'Album', 'VotarFavoritoC'];

    function CancionDetailController($scope, $rootScope, $stateParams, previousState, entity, Cancion, Album, VotarFavoritoC) {
        var vm = this;

        vm.cancion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appApp:cancionUpdate', function(event, result) {
            vm.cancion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
