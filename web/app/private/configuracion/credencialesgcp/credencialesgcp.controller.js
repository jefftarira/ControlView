(function () {
  'use strict';

  angular
    .module('app')
    .controller('credencialesGcpController', credencialesGcpController);

    credencialesGcpController.$inject = ["$window", "$state", "credencialesGcpFactory"];

  function credencialesGcpController($window, $state, credencialesGcpFactory) {
    var vm = this;
    vm.getData = getData;
    vm.guardar = guardar;
    vm.data = credencialesGcpFactory.credencial;

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


    getData();

    function getData() {
      return credencialesGcpFactory.getCredencial()
        .then(function () {
          vm.data = credencialesGcpFactory.credencial;
        })
        .catch(function (error) {
          if (error.tipo == 2) location.reload();
          toastr.options = tos;
          console.log(error);
          toastr.error(error.mensaje, "Ocurrió un problema!.");
        })
        .finally(function () {
          console.log(vm.data);
        });
    }

    function guardar() {
      var l = $(".ladda-button").ladda();
      l.ladda("start");
      return credencialesGcpFactory.guardar()
        .then(function () {
          getData();
          toastr.options = tos;
          toastr.success("Se guardo correctamente la configuracion ");

        })
        .catch(function (error) {
          if (error.tipo == 2) location.reload();
          toastr.options = tos;
          console.log(error);
          toastr.error(error.mensaje, "Ocurrió un problema!.");
        })
        .finally(function () {
          l.ladda("stop");
        });
    }

  }
})();