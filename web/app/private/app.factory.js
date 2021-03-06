(function () {
  'use strict';
  angular
    .module('app')
    .factory('global', global);

  global.$inject = ['$http', '$q'];

  function global($http, $q) {

    var factory = {
      getDataConfig: getDataConfig,
      cerrarSesion: cerrarSesion,
      dataConfig: {},
      dataSesion: {
        error: false,
        mensaje: ''
      },
      dataError: {
        error: false,
        mensaje: ''
      }
    };

    return factory;

    function getDataConfig() {
      var q = $q.defer();
      $http.get('configuracion.json')
        .success(function (data) {
          factory.dataConfig = data;
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

    function cerrarSesion() {
      var q = $q.defer();
      $http.post('cerrarSesion')
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