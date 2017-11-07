(function() {
    'use strict';
    angular
        .module('appApp')
        .factory('VotarFavoritoM', VotarFavoritoM);

    VotarFavoritoM.$inject = ['$resource', 'DateUtils'];

    function VotarFavoritoM ($resource, DateUtils) {
        var resourceUrl =  'api/votar-favorito-ms/:id';

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
