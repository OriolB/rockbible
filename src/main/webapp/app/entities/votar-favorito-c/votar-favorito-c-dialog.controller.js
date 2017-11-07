(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('VotarFavoritoCDialogController', VotarFavoritoCDialogController);

    VotarFavoritoCDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'VotarFavoritoC', 'UserExt', 'Cancion'];

    function VotarFavoritoCDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, VotarFavoritoC, UserExt, Cancion) {
        var vm = this;

        vm.votarFavoritoC = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.userexts = UserExt.query();
        vm.cancions = Cancion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.votarFavoritoC.id !== null) {
                VotarFavoritoC.update(vm.votarFavoritoC, onSaveSuccess, onSaveError);
            } else {
                VotarFavoritoC.save(vm.votarFavoritoC, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appApp:votarFavoritoCUpdate', result);
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
