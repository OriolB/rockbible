(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('MusicoController', MusicoController);

    MusicoController.$inject = ['DataUtils', 'Musico'];

    function MusicoController(DataUtils, Musico) {

        var vm = this;

        vm.musicos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Musico.query(function(result) {
                vm.musicos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
