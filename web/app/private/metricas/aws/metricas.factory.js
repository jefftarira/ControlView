(function () {
  'use strict';
  angular
    .module('app')
    .factory('metricasFactory', metricasFactory);

  metricasFactory.$inject = ['$http', '$q'];

  function metricasFactory($http, $q) {

    var factory = {

      guardarHistorico: guardarHistorico,

      getDataClusters: getDataClusters,
      infoclusters: {},

      getInfoS3Files: getInfoS3Files,
      infos3files: [],

      getInfoTablesEmr: getInfoTablesEmr,
      infoTablesEmr: {
        loading: false,
        data: [],
      },



      getFilesListCalifornia: getFilesListCalifornia,
      checkStatusStepEmr: checkStatusStepEmr,
      getFilesListPekin: getFilesListPekin,
      data: {
        cities: [
          { name: 'california' },
          { name: 'pekin' }
        ],
        listFiles: [],
        listFileloading: false,
        resultado: {
          stepId: '',
          stepStatus: '',
          loading: false,
          time: 0,
          totalPuntos: 0,
          tipoInstancias: ''
        },
        filtros: {
          city: '',
          archivo: '',
          iteraciones: 1,
          clusters: 2,
        },
        cpPuntos: [],
        cpResumen: []
      },
      getClustering: getClustering,
      procesarPuntos: procesarPuntos,
      checkStatusStepEmr: checkStatusStepEmr

    };

    return factory;

    function getDataClusters() {
      var q = $q.defer();
      $http.get('getListClusters')
        .success(function (data) {
          factory.infoclusters = data;
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

    function getInfoS3Files() {
      var q = $q.defer();
      $http.get('getListFilesS3')
        .success(function (data) {
          factory.infos3files = data.files_s3;
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

    function getInfoTablesEmr() {
      var q = $q.defer();
      $http.get('countRowsAws')
        .success(function (data) {
          factory.infoTablesEmr.data = data;
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

    function getFilesListCalifornia() {
      var q = $q.defer();
      $http.get('app/private/metricas/aws/filesCalifornia.json')
        .success(function (data) {
          factory.data.listFiles = data;
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

    // Methods Pekin

    function getFilesListPekin() {
      var q = $q.defer();
      $http.get('app/private/metricas/aws/filesPekin.json')
        .success(function (data) {
          factory.data.listFiles = data;
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

    function getClustering() {
      var params = {
        tableName: factory.data.filtros.city
      }
      var q = $q.defer();
      $http.post('dataClusteringCalifornia', params)
        .success(function (data) {
          q.resolve(data);
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

    function procesarPuntos() {
      var params = {
        archivo: factory.data.filtros.archivo,
        iteraciones: factory.data.filtros.iteraciones,
        clusters: factory.data.filtros.clusters,
        clusterID: factory.infoclusters.aws.id,
        tableName: factory.data.filtros.city
      }
      var q = $q.defer();
      $http.post('launchStepEmr', params)
        .success(function (data) {
          factory.data.resultado.stepId = data.stepID;
          factory.data.resultado.stepStatus = data.stepStatus;
          factory.data.resultado.tipoInstancias = data.tipoInstancias;
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

    function checkStatusStepEmr() {
      var params = {
        stepId: factory.data.resultado.stepId,
        clusterID: factory.infoclusters.aws.id,
      }
      var q = $q.defer();
      $http.post('checkStepEmr', params)
        .success(function (data) {
          factory.data.resultado.stepStatus = data.stepStatus;
          factory.data.resultado.time = data.time;
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

    function guardarHistorico() {
      var params = {
        cloud: 'AWS',
        algoritmo: 'K-means ' + factory.data.filtros.city,
        maquinas: factory.data.resultado.tipoInstancias,
        registros: factory.data.resultado.totalPuntos,
        tiempo: factory.data.resultado.time
      }

      console.log(params);
      var q = $q.defer();
      $http.post('insertHistorico', params)
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