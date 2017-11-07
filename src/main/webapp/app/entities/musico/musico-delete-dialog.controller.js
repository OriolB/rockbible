(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('MusicoDeleteController',MusicoDeleteController);

    MusicoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Musico'];

    function MusicoDeleteController($uibModalInstance, entity, Musico) {
        var vm = this;

        vm.musico = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Musico.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
