(function() {
    'use strict';
    angular
        .module('appApp')
        .factory('VotarFavoritoA', VotarFavoritoA);

    VotarFavoritoA.$inject = ['$resource', 'DateUtils'];

    function VotarFavoritoA ($resource, DateUtils) {
        var resourceUrl =  'api/votar-favorito-as/:id';

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
