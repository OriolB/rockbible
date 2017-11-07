(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('VotarFavoritoADeleteController',VotarFavoritoADeleteController);

    VotarFavoritoADeleteController.$inject = ['$uibModalInstance', 'entity', 'VotarFavoritoA'];

    function VotarFavoritoADeleteController($uibModalInstance, entity, VotarFavoritoA) {
        var vm = this;

        vm.votarFavoritoA = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            VotarFavoritoA.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
