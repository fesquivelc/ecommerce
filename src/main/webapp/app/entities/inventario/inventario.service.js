(function() {
    'use strict';
    angular
        .module('ecommerceApp')
        .factory('Inventario', Inventario);

    Inventario.$inject = ['$resource'];

    function Inventario ($resource) {
        var resourceUrl =  'api/inventarios/:id';

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
