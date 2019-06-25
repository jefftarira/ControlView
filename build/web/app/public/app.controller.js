(function () {
  'use strict';

  angular
    .module('app')
    .controller('mainController', mainController);

  mainController.$inject = ['configuracion'];

  function mainController(configuracion) {
    var cg = this;
    cg.config = {};

    getConfig();

    function getConfig() {
      return configuracion.getDataConfig()
        .then(function () {
          cg.config = configuracion.dataConfig;
        })
        .catch(function (error) {
          toastr.options = tos;
          console.log(error);
          toastr.error("", "Ocurrió un problema al cargar datos!");
        });
    }
  }

})();