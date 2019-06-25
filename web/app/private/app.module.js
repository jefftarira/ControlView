(function () {
  'use strict';

  angular
    .module('app',
    ['ui.router', 'ui.bootstrap', 'datePicker',
      'localytics.directives', 'ui.utils.masks',
      'monospaced.elastic','chart.js'])
    .filter('capitalize', capitalize)
    .filter('limitText', limitText);



  function capitalize() {
    return function (text) {
      if (text != null) {
        var palabras = text.split(" ");
        var nuevoTexto = "";

        for (var i = 0; i < palabras.length; i++) {
          nuevoTexto += palabras[i].substring(0, 1).toUpperCase() + palabras[i].substring(1).toLowerCase() + " ";
        }
        return nuevoTexto;
      }
    };
  }

  function limitText() {
    return function (text, limit, ellipsis) {
      if (text !== undefined) {
        if (isNaN(limit)) {
          limit = 450;
        }

        if (ellipsis === undefined) {
          ellipsis = "";
        }

        if (text.length <= limit || text.length - ellipsis.length <= limit) {
          return text;
        } else {
          return String(text).substring(0, limit - ellipsis.length) + ellipsis;
        }
      }
    };
  }

})();