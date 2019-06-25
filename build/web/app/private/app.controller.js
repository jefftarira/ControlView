(function () {
  'use strict';

  angular
    .module('app')
    .controller('mainController', mainController);

  mainController.$inject = ["$state", "global"];

  function mainController($state, global) {
    var cg = this;
    cg.getConfig = getConfig;

    cg.config = {};
    cg.loading = false;

    getConfig();

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

    function getConfig() {
      cg.loading = true;
      return global.getDataConfig()
        .then(function () {
          cg.config = global.dataConfig;
        })
        .catch(function (error) {
          toastr.options = tos;
          console.log(error);
          toastr.error("", "Ocurrió un problema al cargar datos!");
        })
        .finally(function () {
          cg.loading = false;
        });
    }
  }
})();