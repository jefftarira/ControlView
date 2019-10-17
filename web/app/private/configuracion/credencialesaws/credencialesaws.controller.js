(function () {
  'use strict';

  angular
    .module('app')
    .controller('credencialesAwsController', credencialesAwsController);

  credencialesAwsController.$inject = ["$window", "$state", "credencialesAwsFactory"];

  function credencialesAwsController($window, $state, credencialesAwsFactory) {
    var vm = this;

    vm.getData = getData;
    vm.getRegiones = getRegiones;
    vm.guardar = guardar;
    vm.data = credencialesAwsFactory.credencial;
    vm.regiones = [];

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

    getRegiones();
    getData();

    function getData() {
      return credencialesAwsFactory.getCredencial()
        .then(function () {
          vm.data = credencialesAwsFactory.credencial;
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
      return credencialesAwsFactory.guardar()
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

    function getRegiones() {
      vm.regiones = credencialesAwsFactory.regiones;
    }

  }
})();