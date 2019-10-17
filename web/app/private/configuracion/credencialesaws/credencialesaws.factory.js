(function () {
  'use strict';
  angular
    .module('app')
    .factory('credencialesAwsFactory', credencialesAwsFactory);

  credencialesAwsFactory.$inject = ['$http', '$q'];

  function credencialesAwsFactory($http, $q) {

    var factory = {
      getCredencial: getCredencial,
      guardar: guardar,
      credencial: {},
      cargando: false,
      regiones: [
        { "name": "EE.UU. Este (Ohio)", "region": "us-east-2" },
        { "name": "EE.UU. Este (Norte de Virginia)", "region": "us-east-1" },
        { "name": "EE.UU. Oeste (Norte de California)", "region": "us-west-1" },
        { "name": "EE.UU. Oeste (Oregón)", "region": "us-west-2" },
        { "name": "Canadá (Central)", "region": "ca-central-1" },        
        { "name": "América del Sur (São Paulo)", "region": "sa-east-1" }
      ],
    };

    return factory;

    function getCredencial() {
      var q = $q.defer();
      $http.get('credencialesaws')
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
      $http.post('guardarcredencialesaws', factory.credencial)
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