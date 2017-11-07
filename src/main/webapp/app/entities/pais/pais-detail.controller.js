(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('PaisDetailController', PaisDetailController);

    PaisDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pais', 'Ciudad', 'Musico', 'Banda'];

    function PaisDetailController($scope, $rootScope, $stateParams, previousState, entity, Pais, Ciudad, Musico, Banda) {
        var vm = this;

        vm.pais = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appApp:paisUpdate', function(event, result) {
            vm.pais = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
