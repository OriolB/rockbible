(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('CancionDialogController', CancionDialogController);

    CancionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cancion', 'Album', 'VotarFavoritoC'];

    function CancionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cancion, Album, VotarFavoritoC) {
        var vm = this;

        vm.cancion = entity;
        vm.clear = clear;
        vm.save = save;
        vm.albums = Album.query();
        vm.votarfavoritocs = VotarFavoritoC.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cancion.id !== null) {
                Cancion.update(vm.cancion, onSaveSuccess, onSaveError);
            } else {
                Cancion.save(vm.cancion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appApp:cancionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
