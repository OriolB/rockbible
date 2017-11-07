(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('BandaDetailController', BandaDetailController);

    BandaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Banda', 'Pais', 'Genero', 'Musico', 'Album', 'VotarFavoritoB', 'Discografica'];

    function BandaDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Banda, Pais, Genero, Musico, Album, VotarFavoritoB, Discografica) {
        var vm = this;

        vm.banda = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appApp:bandaUpdate', function(event, result) {
            vm.banda = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
