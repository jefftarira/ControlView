(function () {
  'use strict';
  angular
    .module('app')
    .factory('logsFactory', logsFactory);

  logsFactory.$inject = ['$http', '$q'];

  function logsFactory($http, $q) {

    var factory = {
      registrarEvento: registrarEvento,
    };

    return factory;

    function registrarEvento(modulo) {
      var params = {
        modulo: modulo,
      };
      var q = $q.defer();
      $http.post('guardarRegistroLog', params)
        .success(function (data) {
          q.resolve(data);
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

  }
})();