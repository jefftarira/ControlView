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
        templateUrl: "app/private/inicio/inicio.html",
        controller: "inicioController",
        controllerAs: "vm",
      })
      .state("metricas", {
        abstract: true,
        url: "/metricas",
        templateUrl: "app/private/metricas/metricas.html"
      })
      .state("metricas.aws", {
        url: "/aws",
        templateUrl: "app/private/metricas/aws/vista.html",
        controller: "metricController",
        controllerAs: "vm",
        data: {
          pageTitle: "Metricas AWS"
        }
      })
      .state("metricas.gcp", {
        url: "/gcp",
        templateUrl: "app/private/metricas/gcp/vista.html",
        controller: "metricGcpController",
        controllerAs: "vm",
        data: {
          pageTitle: "Metricas GCP"
        }
      })
      .state("metricas.historico", {
        url: "/historial",
        templateUrl: "app/private/metricas/historico/historico.html",
        controller: "historicoController",
        controllerAs: "vm",
        data: {
          pageTitle: "Historial"
        }
      })
      .state("log", {
        url: "/log",
        templateUrl: "app/private/logs/logs.html",
        controller: "logsController",
        controllerAs: "vm",
        data: {
          pageTitle: "Log"
        }
      })
      .state("configuracion", {
        abstract: true,
        url: "/configuracion",
        templateUrl: "app/private/configuracion/configuracion.html"
      })
      .state("configuracion.credencialesaws", {
        url: "/credencialesaws",
        templateUrl: "app/private/configuracion/credencialesaws/vista.html",
        controller: "credencialesAwsController",
        controllerAs: "vm",
        data: {
          pageTitle: "Credenciales AWS"
        }
      })
      .state("configuracion.credencialesgcp", {
        url: "/credencialesgcp",
        templateUrl: "app/private/configuracion/credencialesgcp/vista.html",
        controller: "credencialesGcpController",
        controllerAs: "vm",
        data: {
          pageTitle: "Credenciales GCP"
        }
      });
  }

})();