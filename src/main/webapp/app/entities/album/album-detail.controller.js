(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('AlbumDetailController', AlbumDetailController);

    AlbumDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Album', 'Banda', 'Musico', 'Genero', 'VotarFavoritoA', 'Cancion'];

    function AlbumDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Album, Banda, Musico, Genero, VotarFavoritoA, Cancion) {
        var vm = this;

        vm.album = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appApp:albumUpdate', function(event, result) {
            vm.album = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
