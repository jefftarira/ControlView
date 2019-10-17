(function () {
  'use strict';
  angular
    .module('app')
    .factory('credencialesGcpFactory', credencialesGcpFactory);

  credencialesGcpFactory.$inject = ['$http', '$q'];

  function credencialesGcpFactory($http, $q) {

    var factory = {
      getCredencial: getCredencial,
      guardar: guardar,
      credencial: {},
      cargando: false,
    };

    return factory;

    function getCredencial() {
      var q = $q.defer();
      $http.get('credencialesgcp')
        .success(function (data) {
          factory.credencial = data;
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

    function guardar() {
      var q = $q.defer();
      $http.post('guardarcredencialesgcp', factory.credencial)
        .success(function (data) {
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }



  }
})();