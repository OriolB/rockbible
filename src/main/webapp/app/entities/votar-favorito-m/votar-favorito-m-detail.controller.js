(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('VotarFavoritoMDetailController', VotarFavoritoMDetailController);

    VotarFavoritoMDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'VotarFavoritoM', 'UserExt', 'Musico'];

    function VotarFavoritoMDetailController($scope, $rootScope, $stateParams, previousState, entity, VotarFavoritoM, UserExt, Musico) {
        var vm = this;

        vm.votarFavoritoM = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appApp:votarFavoritoMUpdate', function(event, result) {
            vm.votarFavoritoM = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
