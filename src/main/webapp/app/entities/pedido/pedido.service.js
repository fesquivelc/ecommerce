(function() {
    'use strict';
    angular
        .module('ecommerceApp')
        .factory('Pedido', Pedido);

    Pedido.$inject = ['$resource', 'DateUtils'];

    function Pedido ($resource, DateUtils) {
        var resourceUrl =  'api/pedidos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fecha = DateUtils.convertDateTimeFromServer(data.fecha);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
