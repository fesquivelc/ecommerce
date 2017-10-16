(function() {
    'use strict';
    angular
        .module('ecommerceApp')
        .factory('Cuota', Cuota);

    Cuota.$inject = ['$resource', 'DateUtils'];

    function Cuota ($resource, DateUtils) {
        var resourceUrl =  'api/cuotas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaVencimiento = DateUtils.convertLocalDateFromServer(data.fechaVencimiento);
                        data.fechaComunicado = DateUtils.convertLocalDateFromServer(data.fechaComunicado);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaVencimiento = DateUtils.convertLocalDateToServer(copy.fechaVencimiento);
                    copy.fechaComunicado = DateUtils.convertLocalDateToServer(copy.fechaComunicado);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaVencimiento = DateUtils.convertLocalDateToServer(copy.fechaVencimiento);
                    copy.fechaComunicado = DateUtils.convertLocalDateToServer(copy.fechaComunicado);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
