(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('MusicoDialogController', MusicoDialogController);

    MusicoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Musico', 'Banda', 'Pais', 'Album', 'VotarFavoritoM'];

    function MusicoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Musico, Banda, Pais, Album, VotarFavoritoM) {
        var vm = this;

        vm.musico = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.bandas = Banda.query();
        vm.pais = Pais.query();
        vm.albums = Album.query();
        vm.votarfavoritoms = VotarFavoritoM.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.musico.id !== null) {
                Musico.update(vm.musico, onSaveSuccess, onSaveError);
            } else {
                Musico.save(vm.musico, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appApp:musicoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaNacimiento = false;

        vm.setFoto = function ($file, musico) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        musico.foto = base64Data;
                        musico.fotoContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
