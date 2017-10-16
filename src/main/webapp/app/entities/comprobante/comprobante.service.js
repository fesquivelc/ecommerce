(function() {
    'use strict';
    angular
        .module('ecommerceApp')
        .factory('Comprobante', Comprobante);

    Comprobante.$inject = ['$resource'];

    function Comprobante ($resource) {
        var resourceUrl =  'api/comprobantes/:id';

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
