(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('VotarFavoritoMDialogController', VotarFavoritoMDialogController);

    VotarFavoritoMDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'VotarFavoritoM', 'UserExt', 'Musico'];

    function VotarFavoritoMDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, VotarFavoritoM, UserExt, Musico) {
        var vm = this;

        vm.votarFavoritoM = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.userexts = UserExt.query();
        vm.musicos = Musico.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.votarFavoritoM.id !== null) {
                VotarFavoritoM.update(vm.votarFavoritoM, onSaveSuccess, onSaveError);
            } else {
                VotarFavoritoM.save(vm.votarFavoritoM, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appApp:votarFavoritoMUpdate', result);
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
