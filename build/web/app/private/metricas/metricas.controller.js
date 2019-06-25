(function () {
  'use strict';

  angular
    .module('app')
    .controller('metricController', metricController);

  metricController.$inject = ["$window", "$state", "logsFactory", "metricasFactory"];

  function metricController($window, $state, logsFactory, metricasFactory) {
    var vm = this;
    vm.registrarlog = registrarlog;
    vm.getChartTotalCity = getChartTotalCity;
    vm.cargando = false;
    vm.chartTotalCity = metricasFactory.chartTotalCity;

    $window.scrollTo(0, 0);
    var tos = {
      closeButton: true,
      debug: false,
      progressBar: true,
      preventDuplicates: false,
      positionClass: "toast-bottom-right",
      showDuration: "300",
      hideDuration: "300",
      timeOut: "3000",
      extendedTimeOut: "1000",
      showEasing: "swing",
      hideEasing: "linear",
      showMethod: "fadeIn",
      hideMethod: "fadeOut"
    };

    registrarlog();
    getChartTotalCity();

    function registrarlog() {
      return logsFactory.registrarEvento('Metricas')
        .then(function () {
          console.log("Se registro evento en log");
        })
        .catch(function (error) {
          console.log(error);
        });
    }

    function getChartTotalCity() {
      vm.chartTotalCity.cargando = true;
      return metricasFactory.getChartTotalCity()
        .then(function () {
          vm.chartTotalCity = metricasFactory.chartTotalCity;
          console.log(vm.chartTotalCity);
        })
        .catch(function (error) {
          console.log(error);
        })
        .finally(function () {
          vm.chartTotalCity.cargando = false;
        });
    }
  }
})();