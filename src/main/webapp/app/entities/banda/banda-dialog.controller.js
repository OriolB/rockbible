(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('BandaDialogController', BandaDialogController);

    BandaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Banda', 'Pais', 'Genero', 'Musico', 'Album', 'VotarFavoritoB', 'Discografica'];

    function BandaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Banda, Pais, Genero, Musico, Album, VotarFavoritoB, Discografica) {
        var vm = this;

        vm.banda = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.pais = Pais.query();
        vm.generos = Genero.query();
        vm.musicos = Musico.query();
        vm.albums = Album.query();
        vm.votarfavoritobs = VotarFavoritoB.query();
        vm.discograficas = Discografica.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.banda.id !== null) {
                Banda.update(vm.banda, onSaveSuccess, onSaveError);
            } else {
                Banda.save(vm.banda, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appApp:bandaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaCreacion = false;
        vm.datePickerOpenStatus.yearsActivos = false;

        vm.setFoto = function ($file, banda) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        banda.foto = base64Data;
                        banda.fotoContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
