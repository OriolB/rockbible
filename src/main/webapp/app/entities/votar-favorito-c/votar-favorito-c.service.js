(function() {
    'use strict';
    angular
        .module('appApp')
        .factory('VotarFavoritoC', VotarFavoritoC);

    VotarFavoritoC.$inject = ['$resource', 'DateUtils'];

    function VotarFavoritoC ($resource, DateUtils) {
        var resourceUrl =  'api/votar-favorito-cs/:id';

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
