(function () {
  'use strict';

  angular
    .module('app')
    .controller('inicioController', inicioController);

  inicioController.$inject = ["$window", "$state", "logsFactory"];

  function inicioController($window, $state, logsFactory) {
    var vm = this;
    vm.registrarlog = registrarlog;

    $window.scrollTo(0, 0);

    registrarlog();
    function registrarlog() {
      return logsFactory.registrarEvento('Inicio')
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
  }
})();