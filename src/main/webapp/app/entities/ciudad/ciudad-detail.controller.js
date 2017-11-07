(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('CiudadDetailController', CiudadDetailController);

    CiudadDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ciudad', 'Pais'];

    function CiudadDetailController($scope, $rootScope, $stateParams, previousState, entity, Ciudad, Pais) {
        var vm = this;

        vm.ciudad = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appApp:ciudadUpdate', function(event, result) {
            vm.ciudad = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
