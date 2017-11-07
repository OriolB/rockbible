(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('GeneroController', GeneroController);

    GeneroController.$inject = ['Genero'];

    function GeneroController(Genero) {

        var vm = this;

        vm.generos = [];

        loadAll();

        function loadAll() {
            Genero.query(function(result) {
                vm.generos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
