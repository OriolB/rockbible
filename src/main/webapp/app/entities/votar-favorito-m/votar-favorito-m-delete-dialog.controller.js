(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('VotarFavoritoMDeleteController',VotarFavoritoMDeleteController);

    VotarFavoritoMDeleteController.$inject = ['$uibModalInstance', 'entity', 'VotarFavoritoM'];

    function VotarFavoritoMDeleteController($uibModalInstance, entity, VotarFavoritoM) {
        var vm = this;

        vm.votarFavoritoM = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            VotarFavoritoM.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
