(function () {
  
  'use strict';
  
  angular
    .module('app')
    .config(route);

  function route($routeProvider){
    $routeProvider
      .when('/',{
      templateUrl   : 'app/public/login/login.jsp',
      controller    : 'loginController',
      controllerAs  : 'vm'      
    }).otherwise({
      redirectTo    : '/'
    });  
  }

})();