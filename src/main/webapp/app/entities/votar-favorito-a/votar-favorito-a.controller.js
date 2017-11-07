(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('VotarFavoritoAController', VotarFavoritoAController);

    VotarFavoritoAController.$inject = ['VotarFavoritoA'];

    function VotarFavoritoAController(VotarFavoritoA) {

        var vm = this;

        vm.votarFavoritoAS = [];

        loadAll();

        function loadAll() {
            VotarFavoritoA.query(function(result) {
                vm.votarFavoritoAS = result;
                vm.searchQuery = null;
            });
        }
    }
})();
