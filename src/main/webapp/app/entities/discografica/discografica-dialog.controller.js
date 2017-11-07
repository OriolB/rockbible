(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('DiscograficaDialogController', DiscograficaDialogController);

    DiscograficaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Discografica', 'Banda'];

    function DiscograficaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Discografica, Banda) {
        var vm = this;

        vm.discografica = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.bandas = Banda.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.discografica.id !== null) {
                Discografica.update(vm.discografica, onSaveSuccess, onSaveError);
            } else {
                Discografica.save(vm.discografica, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appApp:discograficaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setFoto = function ($file, discografica) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        discografica.foto = base64Data;
                        discografica.fotoContentType = $file.type;
                    });
                });
            }
        };

    }
})();
