(function() {
    'use strict';
    angular
        .module('ecommerceApp')
        .factory('Direccion', Direccion);

    Direccion.$inject = ['$resource'];

    function Direccion ($resource) {
        var resourceUrl =  'api/direccions/:id';

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
