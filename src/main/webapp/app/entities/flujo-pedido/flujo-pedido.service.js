(function() {
    'use strict';
    angular
        .module('ecommerceApp')
        .factory('FlujoPedido', FlujoPedido);

    FlujoPedido.$inject = ['$resource'];

    function FlujoPedido ($resource) {
        var resourceUrl =  'api/flujo-pedidos/:id';

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
