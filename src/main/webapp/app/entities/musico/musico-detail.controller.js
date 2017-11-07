(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('MusicoDetailController', MusicoDetailController);

    MusicoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Musico', 'Banda', 'Pais', 'Album', 'VotarFavoritoM'];

    function MusicoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Musico, Banda, Pais, Album, VotarFavoritoM) {
        var vm = this;

        vm.musico = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appApp:musicoUpdate', function(event, result) {
            vm.musico = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
