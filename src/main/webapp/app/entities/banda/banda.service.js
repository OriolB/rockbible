(function() {
    'use strict';
    angular
        .module('appApp')
        .factory('Banda', Banda);

    Banda.$inject = ['$resource', 'DateUtils'];

    function Banda ($resource, DateUtils) {
        var resourceUrl =  'api/bandas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaCreacion = DateUtils.convertLocalDateFromServer(data.fechaCreacion);
                        data.yearsActivos = DateUtils.convertLocalDateFromServer(data.yearsActivos);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaCreacion = DateUtils.convertLocalDateToServer(copy.fechaCreacion);
                    copy.yearsActivos = DateUtils.convertLocalDateToServer(copy.yearsActivos);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaCreacion = DateUtils.convertLocalDateToServer(copy.fechaCreacion);
                    copy.yearsActivos = DateUtils.convertLocalDateToServer(copy.yearsActivos);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
