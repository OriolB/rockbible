(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('PaisDialogController', PaisDialogController);

    PaisDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pais', 'Ciudad', 'Musico', 'Banda'];

    function PaisDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Pais, Ciudad, Musico, Banda) {
        var vm = this;

        vm.pais = entity;
        vm.clear = clear;
        vm.save = save;
        vm.ciudads = Ciudad.query();
        vm.musicos = Musico.query();
        vm.bandas = Banda.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pais.id !== null) {
                Pais.update(vm.pais, onSaveSuccess, onSaveError);
            } else {
                Pais.save(vm.pais, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appApp:paisUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
