(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('CiudadController', CiudadController);

    CiudadController.$inject = ['Ciudad'];

    function CiudadController(Ciudad) {

        var vm = this;

        vm.ciudads = [];

        loadAll();

        function loadAll() {
            Ciudad.query(function(result) {
                vm.ciudads = result;
                vm.searchQuery = null;
            });
        }
    }
})();
