(function () {
  'use strict';
  angular
    .module('app')
    .factory('metricasFactory', metricasFactory);

  metricasFactory.$inject = ['$http', '$q'];

  function metricasFactory($http, $q) {

    var factory = {
      getChartTotalCity: getChartTotalCity,
      chartTotalCity: {
        options: {
          legend: { display: true },
          scales: {
            xAxes: [{
              gridLines: {
                offsetGridLines: false
              }
            }]
          },
        },
        data: [],
        series: [],
        labels: [],
      },
    };

    return factory;

    function getChartTotalCity() {
      var q = $q.defer();
      $http.get('TotalDataByYearCity')
        .success(function (data) {
          factory.chartTotalCity.data = data.data;
          factory.chartTotalCity.series = data.series;
          factory.chartTotalCity.labels = data.labels;
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

  }
})();