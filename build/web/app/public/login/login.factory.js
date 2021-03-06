(function () {
  'use strict';
  angular
    .module('app')
    .factory('loginFactory', loginFactory);

  loginFactory.$inject = ['$http', '$q'];

  function loginFactory($http, $q) {

    var factory = {
      login: login,
      user: {
        usuario: "",
        clave: "",
        invalido: false,
        mensaje: "",
      }
    };

    return factory;

    function login() {
      var q = $q.defer();
      $http.post('login', factory.user)
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