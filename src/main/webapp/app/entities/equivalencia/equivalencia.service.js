(function() {
    'use strict';
    angular
        .module('ecommerceApp')
        .factory('Equivalencia', Equivalencia);

    Equivalencia.$inject = ['$resource'];

    function Equivalencia ($resource) {
        var resourceUrl =  'api/equivalencias/:id';

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
