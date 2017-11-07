(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('VotarFavoritoMController', VotarFavoritoMController);

    VotarFavoritoMController.$inject = ['VotarFavoritoM'];

    function VotarFavoritoMController(VotarFavoritoM) {

        var vm = this;

        vm.votarFavoritoMS = [];

        loadAll();

        function loadAll() {
            VotarFavoritoM.query(function(result) {
                vm.votarFavoritoMS = result;
                vm.searchQuery = null;
            });
        }
    }
})();
