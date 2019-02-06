(function () {
  'use strict';

  angular
    .module('app')
    .controller('loginController', loginController);

  loginController.$inject = ['loginFactory'];

  function loginController(loginFactory) {
    var vm = this;
    vm.ingresar = ingresar;
    vm.data = loginFactory;

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

    function ingresar() {

      var l = $('.ladda-button').ladda();
      l.ladda('start');

      return loginFactory.login()
        .then(function (data) {          
          location.reload();
        })
        .catch(function (error) {
          l.ladda("stop");
          toastr.options = tos;
          console.log(error);
          if (error.tipoerror == 2) {
            toastr.warning(error.mensaje, "No puede ingresar!");
          } else {
            toastr.error(error.mensaje, "Ocurrió un problema!");
          }
        });
    }
  }
})();