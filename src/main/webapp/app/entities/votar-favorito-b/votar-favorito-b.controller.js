(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('VotarFavoritoBController', VotarFavoritoBController);

    VotarFavoritoBController.$inject = ['VotarFavoritoB'];

    function VotarFavoritoBController(VotarFavoritoB) {

        var vm = this;

        vm.votarFavoritoBS = [];

        loadAll();

        function loadAll() {
            VotarFavoritoB.query(function(result) {
                vm.votarFavoritoBS = result;
                vm.searchQuery = null;
            });
        }
    }
})();
