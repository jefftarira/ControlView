(function () {
  'use strict';
  angular
    .module('app')
    .factory('metricasGcpFactory', metricasGcpFactory);

  metricasGcpFactory.$inject = ['$http', '$q'];

  function metricasGcpFactory($http, $q) {

    var factory = {

      guardarHistorico: guardarHistorico,

      getDataClusters: getDataClusters,
      infoclusters: {},

      getInfoStorageFiles: getInfoStorageFiles,
      infoStoragefiles: [],

      getInfoTables: getInfoTables,
      infoTables: {
        loading: false,
        data: [],
      },



      getFilesListCalifornia: getFilesListCalifornia,      
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
      checkStatusStep: checkStatusStep

    };

    return factory;

    function getDataClusters() {
      var q = $q.defer();
      $http.get('infodataproc')
        .success(function (data) {
          factory.infoclusters = data;
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

    function getInfoStorageFiles() {
      var q = $q.defer();
      $http.get('filesstorage')
        .success(function (data) {
          factory.infoStoragefiles = data.files;
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

    function getInfoTables() {
      var q = $q.defer();
      $http.get('infotablesdataproc')
        .success(function (data) {
          factory.infoTables.data = data;
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

    function getFilesListCalifornia() {
      var q = $q.defer();
      $http.get('app/private/metricas/gcp/filesCalifornia.json')
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
      $http.get('app/private/metricas/gcp/filesPekin.json')
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
      $http.post('dataclustering', params)
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
        clusterID: factory.infoclusters.gcp.id,
        tableName: factory.data.filtros.city
      }
      var q = $q.defer();
      $http.post('launchstepdataproc', params)
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

    function checkStatusStep() {
      var params = {
        stepId: factory.data.resultado.stepId,
        clusterID: factory.infoclusters.gcp.id,
      }
      var q = $q.defer();
      $http.post('checkstep', params)
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
        cloud: 'GCP',
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