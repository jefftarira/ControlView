(function () {
  'use strict';
  angular
    .module('app')
    .factory('historicoFactory', historicoFactory);

  historicoFactory.$inject = ['$http', '$q'];

  function historicoFactory($http, $q) {

    var factory = {
      getData: getData,
      data: []
    };

    return factory;

    function getData() {
      var q = $q.defer();
      $http.get('listahistorico')
        .success(function (data) {
          factory.data = data;
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }
  }
})();