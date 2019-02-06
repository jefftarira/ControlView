(function () {
  'use strict';
  angular
    .module('app')
    .factory('encFactory', encFactory);

  encFactory.$inject = ['$http', '$q'];

  function encFactory($http, $q) {
    var factory = {
      getIniData: getIniData,
      saveInicialData: saveInicialData,
      getPregunta: getPregunta,
      getSubPregunta: getSubPregunta,
      getCalificacion: getCalificacion,
      saveFinalData: saveFinalData,
      iniData: {},
      resultado: {},
      pregunta: {},
      preguntaRespuesta: {},
      tieneSubPreguntas: false,
      subPregunta: {},
      secuenciaSubPregunta: 0,
      finSubPreguntas: false,
      finPreguntas: false,
      secuencia: 0,

      totalPreguntas: 0,
    };

    return factory;

    function getIniData() {
      var q = $q.defer();
      $http.get('getDataInicial')
        .success(function (data) {
          factory.iniData = data.opciones;
          factory.resultado = data.resultado;
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

    function saveInicialData() {
      var q = $q.defer();
      $http.post('saveInicialData', factory.resultado)
        .success(function (data) {
          factory.resultado.id = data.idResultado;
          factory.secuencia = data.secuencia;
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

    function getPregunta(respuestas) {
      var idPregunta;
      if (typeof factory.pregunta.id == 'undefined') {
        idPregunta = 0;
      } else {
        idPregunta = factory.pregunta.id;
      }
      var params = {
        idResultado: factory.resultado.id,
        secuencia: factory.secuencia,
        idPregunta: idPregunta,
        respuestas: respuestas,
      };
      var q = $q.defer();
      $http.post('getPreguntaEncuesta', params)
        .success(function (data) {
          factory.preguntaRespuesta = {};
          factory.finPreguntas = data.finPreguntas;
          if (typeof data.preguntaRespuesta == 'undefined') {
            factory.pregunta = data.pregunta;
            factory.secuencia = data.secuencia;
            factory.totalPreguntas = data.total;
            factory.tieneSubPreguntas = data.tieneSubPreguntas;
            factory.subPregunta = {};
          } else {
            factory.preguntaRespuesta = data.preguntaRespuesta;
            factory.subPregunta = data.subPregunta;
            factory.tieneSubPreguntas = data.tieneSubPreguntas;
          }
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

    function getSubPregunta() {
      var idSubPregunta;
      if (typeof factory.subPregunta.id == 'undefined') {
        idSubPregunta = 0;
      } else {
        idSubPregunta = factory.subPregunta.id;
      }


      var params = {
        idResultado: factory.resultado.id,
        idPregunta: factory.pregunta.id,
        secuenciaSubPregunta: factory.secuenciaSubPregunta,
        idSubPregunta: idSubPregunta,
      };
      var q = $q.defer();
      $http.post('getSubPreguntaEncuesta', params)
        .success(function (data) {
          factory.subPregunta = data.subPregunta;
          factory.secuenciaSubPregunta = data.secuenciaSubPregunta;
          factory.finSubPreguntas = data.finSubPreguntas;
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

    function getCalificacion() {
      var params = {
        idResultado: factory.resultado.id
      };
      var q = $q.defer();
      $http.post('getCalificacion', params)
        .success(function (data) {
          factory.resultado.calificacion = data.calificacion;
          q.resolve();
        })
        .error(function (error) {
          q.reject(error);
        });
      return q.promise;
    }

    function saveFinalData() {
      var params = {
        idResultado: factory.resultado.id,
        calificacion: factory.resultado.calificacion,
        correo: factory.resultado.correo,
        observaciones: factory.resultado.comentario

      };
      var q = $q.defer();
      $http.post('sendCalificacionEncuesta', params)
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