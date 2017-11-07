(function() {
    'use strict';
    angular
        .module('appApp')
        .factory('VotarFavoritoB', VotarFavoritoB);

    VotarFavoritoB.$inject = ['$resource', 'DateUtils'];

    function VotarFavoritoB ($resource, DateUtils) {
        var resourceUrl =  'api/votar-favorito-bs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.momento = DateUtils.convertDateTimeFromServer(data.momento);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
