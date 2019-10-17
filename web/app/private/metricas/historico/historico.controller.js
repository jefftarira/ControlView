(function () {
  'use strict';

  angular
    .module('app')
    .controller('historicoController', historicoController);

  historicoController.$inject = ["$window", "$state", "historicoFactory"];
  function historicoController($window, $state, historicoFactory) {
    var vm = this;

    vm.getData = getData;
    vm.data = historicoFactory.data;

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
      return historicoFactory.getData()
        .then(function () {
          vm.data = historicoFactory.data;
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

  }
})();