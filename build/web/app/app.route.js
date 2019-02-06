(function () {
  'use strict';

  angular
    .module('app')
    .config(route)
    .run(function ($rootScope, $state) {
      $rootScope.$state = $state;
    });

  function route($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/inicio");

    $stateProvider
      .state("inicio", {
        url: "/inicio",
        templateUrl: "app/inicio/inicio.html",
        controller: "inicioController",
        controllerAs: "vm",
      })
      .state("metricas", {
        url: "/metricas",
        templateUrl: "app/metricas/metricas.html",
        controller: "metricController",
        controllerAs: "vm",
        data: {
          pageTitle: "Metricas"
        }
      })
      .state("log", {
        url: "/log",
        templateUrl: "app/logs/logs.html",
        controller: "logsController",
        controllerAs: "vm",
        data: {
          pageTitle: "Log"
        }
      });
  }

})();