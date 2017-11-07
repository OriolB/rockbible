(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('VotarFavoritoBDialogController', VotarFavoritoBDialogController);

    VotarFavoritoBDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'VotarFavoritoB', 'UserExt', 'Banda'];

    function VotarFavoritoBDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, VotarFavoritoB, UserExt, Banda) {
        var vm = this;

        vm.votarFavoritoB = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.userexts = UserExt.query();
        vm.bandas = Banda.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.votarFavoritoB.id !== null) {
                VotarFavoritoB.update(vm.votarFavoritoB, onSaveSuccess, onSaveError);
            } else {
                VotarFavoritoB.save(vm.votarFavoritoB, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appApp:votarFavoritoBUpdate', result);
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
