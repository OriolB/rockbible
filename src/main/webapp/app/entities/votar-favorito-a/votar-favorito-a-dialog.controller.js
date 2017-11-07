(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('VotarFavoritoADialogController', VotarFavoritoADialogController);

    VotarFavoritoADialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'VotarFavoritoA', 'UserExt', 'Album'];

    function VotarFavoritoADialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, VotarFavoritoA, UserExt, Album) {
        var vm = this;

        vm.votarFavoritoA = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.userexts = UserExt.query();
        vm.albums = Album.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.votarFavoritoA.id !== null) {
                VotarFavoritoA.update(vm.votarFavoritoA, onSaveSuccess, onSaveError);
            } else {
                VotarFavoritoA.save(vm.votarFavoritoA, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appApp:votarFavoritoAUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.momento = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
