(function() {
    'use strict';
    angular
        .module('appApp')
        .factory('Genero', Genero);

    Genero.$inject = ['$resource'];

    function Genero ($resource) {
        var resourceUrl =  'api/generos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
