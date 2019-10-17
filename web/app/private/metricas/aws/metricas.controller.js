(function () {
  'use strict';

  angular
    .module('app')
    .controller('metricController', metricController);

  metricController.$inject = ["$window", "$state", "logsFactory", "metricasFactory", '$interval'];

  function metricController($window, $state, logsFactory, metricasFactory, $interval) {
    var vm = this;
    vm.registrarlog = registrarlog;
    vm.guardarHistorico = guardarHistorico;
    vm.cargando = false;

    // nuevo
    // vm.getDataChartCalifornia = getDataChartCalifornia;
    vm.infoclusters = metricasFactory.infoclusters;
    vm.getDataClusters = getDataClusters;

    vm.infos3files = metricasFactory.infos3files;
    vm.getInfoS3Files = getInfoS3Files;

    vm.infoTablesEmr = metricasFactory.infoTablesEmr;
    vm.getInfoTablesEmr = getInfoTablesEmr;


    vm.changeCity = changeCity;
    vm.getFilesListPekin = getFilesListPekin;
    vm.etFilesListCalifornia = getFilesListCalifornia
    vm.getClustering = getClustering;
    vm.procesarPuntos = procesarPuntos;
    vm.checkStatusStepEmr = checkStatusStepEmr;
    vm.data = metricasFactory.data;


    vm.cpPuntosOptions = {
      chart: {
        type: 'scatterChart',
        height: 450,
        color: d3.scale.category10().range(),
        scatter: {
          onlyCircles: false
        },
        showDistX: true,
        showDistY: true,
        tooltipContent: function (key) {
          return '<h3>' + key + '</h3>';
        },
        duration: 350,
        xAxis: {
          axisLabel: 'Latitud',
          tickFormat: function (d) {
            return d3.format('.02f')(d);
          }
        },
        yAxis: {
          axisLabel: 'Longitud',
          tickFormat: function (d) {
            return d3.format('.02f')(d);
          },
          axisLabelDistance: 0
        },
        zoom: {
          enabled: false,
          scaleExtent: [1, 10],
          useFixedDomain: false,
          useNiceScale: false,
          horizontalOff: false,
          verticalOff: false,
          unzoomEventType: 'dblclick.zoom'
        }
      }
    };

    vm.cpRumenOptions = {
      chart: {
        type: 'discreteBarChart',
        color: d3.scale.category10().range(),
        height: 300,
        margin: {
          top: 20,
          right: 20,
          bottom: 50,
          left: 55
        },
        x: function (d) { return d.label; },
        y: function (d) { return d.value; },
        showValues: true,
        valueFormat: function (d) {
          return d3.format(',.0f')(d);
        },
        duration: 500,
        xAxis: {
          axisLabel: 'Clusters'
        },
        yAxis: {
          axisLabel: 'Puntos',
          axisLabelDistance: 0
        }
      }
    };

    $interval(function () {
      if (vm.data.resultado.stepId.length > 0 && vm.data.resultado.stepStatus != 'COMPLETED') {
        checkStatusStepEmr();
      }

    }, 5000);

    getDataClusters();
    getInfoS3Files();
    getInfoTablesEmr();



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


    function changeCity() {
      console.log('cambiando ciudad ' + vm.data.filtros.city)
      if (vm.data.filtros.city == 'california') {
        getFilesListCalifornia();
      }
      else {
        getFilesListPekin();
      }
    }

    function registrarlog() {
      return logsFactory.registrarEvento('Metricas')
        .then(function () {
          console.log("Se registro evento en log");
        })
        .catch(function (error) {
          console.log(error);
        });
    }

    function getDataClusters() {
      return metricasFactory.getDataClusters()
        .then(function () {
          vm.infoclusters = metricasFactory.infoclusters;
        })
        .catch(function (error) {
          if (error.tipo == 2) location.reload();
          toastr.options = tos;
          console.log(error);
          toastr.error(error.mensaje, "Ocurrió un problema!.");
        })
        .finally(function () {
        });
    }

    function getInfoS3Files() {
      return metricasFactory.getInfoS3Files()
        .then(function () {
          vm.infos3files = metricasFactory.infos3files;
        })
        .catch(function (error) {
          if (error.tipo == 2) location.reload();
          toastr.options = tos;
          console.log(error);
          toastr.error(error.mensaje, "Ocurrió un problema!.");
        })
        .finally(function () {
        });
    }

    function getInfoTablesEmr() {
      vm.infoTablesEmr.loading = true;
      return metricasFactory.getInfoTablesEmr()
        .then(function () {
          vm.infoTablesEmr = metricasFactory.infoTablesEmr;
        })
        .catch(function (error) {
          if (error.tipo == 2) location.reload();
          toastr.options = tos;
          console.log(error);
          toastr.error(error.mensaje, "Ocurrió un problema!.");
        })
        .finally(function () {
          vm.infoTablesEmr.loading = false;
        });
    }


    function getFilesListCalifornia() {
      return metricasFactory.getFilesListCalifornia()
        .then(function () {
          vm.data = metricasFactory.data;
        })
        .catch(function (error) {
          if (error.tipo == 2) location.reload();
          toastr.options = tos;
          console.log(error);
          toastr.error(error.mensaje, "Ocurrió un problema!.");
        })
        .finally(function () {
        });
    }

    function getFilesListPekin() {
      return metricasFactory.getFilesListPekin()
        .then(function () {
          vm.data = metricasFactory.data;
        })
        .catch(function (error) {
          if (error.tipo == 2) location.reload();
          toastr.options = tos;
          console.log(error);
          toastr.error(error.mensaje, "Ocurrió un problema!.");
        })
        .finally(function () {
          vm.data.listFileLoading = false;
        });
    }

    function getClustering() {
      var charData = [],
        totalPuntos = 0;
      metricasFactory.getClustering()
        .then(function (data) {
          vm.data.cpPuntos = [];
          var mydata = data;
          for (var i = 0; i < mydata.clusters.length; i++) {
            charData.push({
              key: 'Cluster ' + (i + 1),
              values: []
            });
            for (var j = 0; j < mydata.clusters[i].length; j++) {
              charData[i].values.push({
                x: mydata.clusters[i][j][0],
                y: mydata.clusters[i][j][1],
                size: 0.4,
                shape: 'circle'
              });
              totalPuntos++;
            }

            charData[i].values.push({
              x: mydata.centroids[i][0],
              y: mydata.centroids[i][1],
              size: 0.45,
              shape: 'cross'
            });
          }
          vm.data.cpPuntos = charData;
          vm.data.resultado.totalPuntos = totalPuntos;
          guardarHistorico();
          updateResumen();
        })
        .catch(function (error) {
          if (error.tipo == 2) location.reload();
          toastr.options = tos;
          console.log(error);
          toastr.error(error.mensaje, "Ocurrió un problema!.");
        })
        .finally(function () {
        });
    }

    function updateResumen() {
      vm.data.cpResumen = [];
      vm.data.cpResumen.push({
        key: 'total puntos',
        values: []
      });

      for (var i = 0; i < vm.data.cpPuntos.length; i++) {
        vm.data.cpResumen[0].values.push({
          label: vm.data.cpPuntos[i].key,
          value: (vm.data.cpPuntos[i].values.length - 1)
        });
      }
    }

    function procesarPuntos() {
      vm.data.resultado.loading = true;
      var l = $(".ladda-button").ladda();
      l.ladda("start");
      return metricasFactory.procesarPuntos()
        .then(function () {
          vm.data = metricasFactory.data;
        })
        .catch(function (error) {
          if (error.tipo == 2) location.reload();
          toastr.options = tos;
          console.log(error);
          toastr.error(error.mensaje, "Ocurrió un problema!.");
        })
        .finally(function () {
          vm.data.resultado.loading = false;
          l.ladda("stop");
        });
    }

    function checkStatusStepEmr() {
      return metricasFactory.checkStatusStepEmr()
        .then(function () {
          vm.data = metricasFactory.data;
          console.log("Check status cluster : " + vm.data.resultado.stepStatus);
          if (vm.data.resultado.stepId.length > 0 && (vm.data.resultado.stepStatus === 'COMPLETED')) {
            getClustering();
          }
        })
        .catch(function (error) {
          if (error.tipo == 2) location.reload();
          toastr.options = tos;
          console.log(error);
          toastr.error(error.mensaje, "Ocurrió un problema!.");
        })
        .finally(function () {
        });
    }

    function guardarHistorico() {
      return metricasFactory.guardarHistorico()
        .then(function () {          
        })
        .catch(function (error) {
          if (error.tipo == 2) location.reload();
          toastr.options = tos;
          console.log(error);
          toastr.error(error.mensaje, "Ocurrió un problema!.");
        })
        .finally(function () {
        });
    }

  }
})();