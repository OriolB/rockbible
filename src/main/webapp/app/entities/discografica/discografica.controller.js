(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('DiscograficaController', DiscograficaController);

    DiscograficaController.$inject = ['DataUtils', 'Discografica'];

    function DiscograficaController(DataUtils, Discografica) {

        var vm = this;

        vm.discograficas = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Discografica.query(function(result) {
                vm.discograficas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
