(function () {
  'use strict';
  angular
    .module('app')
    .factory('configuracion', configuracion);

  configuracion.$inject = ['$http', '$q'];

  function configuracion($http, $q) {

    var factory = {
      getDataConfig: getDataConfig,
      dataConfig: {}
    }

    return factory;


    function getDataConfig() {
      var q = $q.defer();
      $http.get('configuracion.json')
        .success(function (data) {
          factory.dataConfig = data;
          q.resolve();
        }).error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

  }

})();