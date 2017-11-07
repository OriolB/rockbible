(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('DiscograficaDetailController', DiscograficaDetailController);

    DiscograficaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Discografica', 'Banda'];

    function DiscograficaDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Discografica, Banda) {
        var vm = this;

        vm.discografica = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appApp:discograficaUpdate', function(event, result) {
            vm.discografica = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
