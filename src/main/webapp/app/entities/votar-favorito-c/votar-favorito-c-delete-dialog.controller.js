(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('VotarFavoritoCDeleteController',VotarFavoritoCDeleteController);

    VotarFavoritoCDeleteController.$inject = ['$uibModalInstance', 'entity', 'VotarFavoritoC'];

    function VotarFavoritoCDeleteController($uibModalInstance, entity, VotarFavoritoC) {
        var vm = this;

        vm.votarFavoritoC = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            VotarFavoritoC.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
