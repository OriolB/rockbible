(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('UserExtDetailController', UserExtDetailController);

    UserExtDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'UserExt', 'User', 'VotarFavoritoM', 'VotarFavoritoB', 'VotarFavoritoA', 'VotarFavoritoC'];

    function UserExtDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, UserExt, User, VotarFavoritoM, VotarFavoritoB, VotarFavoritoA, VotarFavoritoC) {
        var vm = this;

        vm.userExt = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appApp:userExtUpdate', function(event, result) {
            vm.userExt = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
