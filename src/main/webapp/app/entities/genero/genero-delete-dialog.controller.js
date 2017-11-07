(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('GeneroDeleteController',GeneroDeleteController);

    GeneroDeleteController.$inject = ['$uibModalInstance', 'entity', 'Genero'];

    function GeneroDeleteController($uibModalInstance, entity, Genero) {
        var vm = this;

        vm.genero = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Genero.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
