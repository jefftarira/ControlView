(function () {
  'use strict';

  angular
    .module('app')
    .controller('metricController', metricController);

  metricController.$inject = ["$window", "$state", "logsFactory"];

  function metricController($window, $state, logsFactory) {
    var vm = this;
    vm.registrarlog = registrarlog;
    vm.cargando = false;

    $window.scrollTo(0, 0);

    registrarlog();
    function registrarlog() {
      return logsFactory.registrarEvento('Metricas')
        .then(function () {
          console.log("Se registro evento en log");
        })
        .catch(function (error) {
          console.log(error);
        });
    }

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

    vm.lineOptions = {
      title: {
        display: true,
        text: 'Custom Chart Title'
      },
      scales: {
        xAxes: [{
          scaleLabel: {
            display: true,
            labelString: 'Date'
          }
        }],
        yAxes: [{
          scaleLabel: {
            display: true,
            labelString: 'value'
          }
        }]
      },
    };


    vm.lineData = {
      labels: ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio"],
      datasets: [
        {
          label: "Example dataset",
          fillColor: "rgba(220,220,220,0.5)",
          strokeColor: "rgba(220,220,220,1)",
          pointColor: "rgba(220,220,220,1)",
          pointStrokeColor: "#fff",
          pointHighlightFill: "#fff",
          pointHighlightStroke: "rgba(220,220,220,1)",
          data: [65, 59, 80, 81, 56, 55, 40]
        },
        {
          label: "Example dataset",
          fillColor: "rgba(26,179,148,0.5)",
          strokeColor: "rgba(26,179,148,0.7)",
          pointColor: "rgba(26,179,148,1)",
          pointStrokeColor: "#fff",
          pointHighlightFill: "#fff",
          pointHighlightStroke: "rgba(26,179,148,1)",
          data: [28, 48, 40, 19, 86, 27, 90]
        }
      ]
    };
  }
})();