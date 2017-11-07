(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('PaisController', PaisController);

    PaisController.$inject = ['Pais'];

    function PaisController(Pais) {

        var vm = this;

        vm.pais = [];

        loadAll();

        function loadAll() {
            Pais.query(function(result) {
                vm.pais = result;
                vm.searchQuery = null;
            });
        }
    }
})();
