(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('VotarFavoritoBDeleteController',VotarFavoritoBDeleteController);

    VotarFavoritoBDeleteController.$inject = ['$uibModalInstance', 'entity', 'VotarFavoritoB'];

    function VotarFavoritoBDeleteController($uibModalInstance, entity, VotarFavoritoB) {
        var vm = this;

        vm.votarFavoritoB = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            VotarFavoritoB.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
