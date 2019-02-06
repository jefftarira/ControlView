(function () {
  'use strict';

  angular
    .module('app')
    .controller('encController', encController);

  encController.$inject = ['encFactory', "$window", "$state"];

  function encController(encFactory, $window, $state) {
    var vm = this;
    vm.loadIni = loadIni;
    vm.loadPregunta = loadPregunta;
    vm.loadSubPregunta = loadSubPregunta;
    vm.saveInicialData = saveInicialData;
    vm.loadCalificacion = loadCalificacion;
    vm.checkedFun = checkedFun;
    vm.emptyPreguntaRespuesta = emptyPreguntaRespuesta;
    vm.emptySubPregunta = emptySubPregunta;
    vm.saveFinalData = saveFinalData;
    vm.reloadState = reloadState;
    vm.checkedItems = [];
    vm.checkedCount = 0;
    vm.finalReporte = false;

    vm.data = encFactory;
    vm.cargando = false;

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

    loadIni();

    function reloadState() {
      $state.go($state.current, {}, { reload: true });
    }

    function loadIni() {
      encFactory.iniData = {};
      encFactory.resultado = {};
      encFactory.pregunta = {};
      encFactory.preguntaRespuesta = {};
      encFactory.subPregunta = {};
      encFactory.secuenciaSubPregunta = 0;
      encFactory.finSubPreguntas = false;
      encFactory.secuencia = 0;
      encFactory.totalPreguntas = 0;
      encFactory.finPreguntas = false;

      vm.cargando = true;
      return encFactory.getIniData()
        .then(function () {
          vm.data = encFactory;
        })
        .catch(function (error) {
          toastr.options = tos;
          console.log(error);
          toastr.error(error.mensaje, "Ocurrió un problema!");
        })
        .finally(function () { vm.cargando = false; });
    }

    function saveInicialData() {
      vm.cargando = true;
      return encFactory.saveInicialData()
        .then(function () {
          vm.data = encFactory;
          if (vm.data.resultado.id !== 0) {
            loadPregunta();
          }
        })
        .catch(function (error) {
          toastr.options = tos;
          console.log(error);
          toastr.error(error.mensaje, "Ocurrió un problema!");
        })
        .finally(function () { vm.cargando = false; });
    }

    function loadPregunta() {
      vm.cargando = true;
      return encFactory.getPregunta(vm.checkedItems)
        .then(function () {
          vm.data = encFactory;
          vm.checkedItems = [];
          vm.checkedCount = 0;
          encFactory.secuenciaSubPregunta = 0;
          encFactory.finSubPreguntas = false;
          if (vm.data.tieneSubPreguntas) {
            loadSubPregunta();
          }
          if (vm.data.finPreguntas === true) {
            loadCalificacion();
          }
        })
        .catch(function (error) {
          toastr.options = tos;
          console.log(error);
          toastr.error(error.mensaje, "Ocurrió un problema!");
        })
        .finally(function () { vm.cargando = false; });
    }

    function loadSubPregunta() {
      if (vm.data.finSubPreguntas === true) {
        loadPregunta();
        return;
      }

      vm.cargando = true;
      return encFactory.getSubPregunta()
        .then(function () {
          vm.data = encFactory;
          vm.checkedItems = [];
          vm.checkedCount = 0;
        })
        .catch(function (error) {
          toastr.options = tos;
          console.log(error);
          toastr.error(error.mensaje, "Ocurrió un problema!");
        })
        .finally(function () { vm.cargando = false; });
    }

    function loadCalificacion() {
      vm.cargando = true;
      return encFactory.getCalificacion()
        .then(function () {
          vm.data = encFactory;
        })
        .catch(function (error) {
          toastr.options = tos;
          console.log(error);
          toastr.error(error.mensaje, "Ocurrió un problema!");
        })
        .finally(function () { vm.cargando = false; });
    }


    angular.forEach(vm.data.pregunta.respuestas, function (res) {
      if (res.checked === true) {
        vm.checkedCount++;
        vm.checkedItems.push(res.id);
      }
    });

    angular.forEach(vm.data.subPregunta.respuestas, function (res) {
      if (res.checked === true) {
        vm.checkedCount++;
        vm.checkedItems.push(res.id);
      }
    });

    function checkedFun(res) {
      if (res.checked) {
        vm.checkedCount++;
        res.checked = true;
        vm.checkedItems.push(res.id);
      } else {
        vm.checkedCount--;
        res.checked = false;
        var findId = vm.checkedItems.indexOf(res.id);
        vm.checkedItems.splice(findId, 1);
      }
    }

    function emptyPreguntaRespuesta() {
      return angular.equals({}, vm.data.preguntaRespuesta);
    }

    function emptySubPregunta() {
      return angular.equals({}, vm.data.subPregunta);
    }

    function saveFinalData() {
      vm.cargando = true;
      return encFactory.saveFinalData()
        .then(function (data) {
          vm.finalReporte = data.respuesta;
          console.log(vm.finalRepote);
        })
        .catch(function (error) {
          toastr.options = tos;
          console.log(error);
          toastr.error(error.mensaje, "Ocurrió un problema!");
        })
        .finally(function () { vm.cargando = false; });
    }
  }
})();