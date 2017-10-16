(function() {
    'use strict';
    angular
        .module('ecommerceApp')
        .factory('UnidadMedida', UnidadMedida);

    UnidadMedida.$inject = ['$resource'];

    function UnidadMedida ($resource) {
        var resourceUrl =  'api/unidad-medidas/:id';

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
