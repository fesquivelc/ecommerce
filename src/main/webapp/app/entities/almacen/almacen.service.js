(function() {
    'use strict';
    angular
        .module('ecommerceApp')
        .factory('Almacen', Almacen);

    Almacen.$inject = ['$resource'];

    function Almacen ($resource) {
        var resourceUrl =  'api/almacens/:id';

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
