(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('VotarFavoritoCController', VotarFavoritoCController);

    VotarFavoritoCController.$inject = ['VotarFavoritoC'];

    function VotarFavoritoCController(VotarFavoritoC) {

        var vm = this;

        vm.votarFavoritoCS = [];

        loadAll();

        function loadAll() {
            VotarFavoritoC.query(function(result) {
                vm.votarFavoritoCS = result;
                vm.searchQuery = null;
            });
        }
    }
})();
