<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html ng-app="app"
  ng-controller="mainController as cg">

  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible"
      content="IE=edge">
    <meta name="viewport"
      content="width=device-width, initial-scale=1.0">
    <title>ControlView</title>
    <link rel="shortcut icon"
      href="aspinia/icons/favicon.ico" />
    <!--Estilos-->
    <link href="aspinia/css/bootstrap.min.css"
      rel="stylesheet">
    <link href="aspinia/font-awesome/css/font-awesome.css"
      rel="stylesheet">
    <link href="aspinia/css/animate.css"
      rel="stylesheet">
    <link href="aspinia/css/style.css"
      rel="stylesheet">
    <link href="aspinia/css/plugins/ladda/ladda-themeless.min.css"
      rel="stylesheet">
    <link href="aspinia/material-icon/css/material-icons.css"
      rel="stylesheet">

    <!--Angular librerias-->
    <script src="angular/lib/angular.min.js"></script>
    <script src="angular/lib/angular-route.min.js"></script>

    <!--Angular Modulos-->
    <script src="app/public/app.module.js"></script>
    <script src="app/public/app.route.js"></script>
    <script src="app/public/app.controller.js"></script>
    <script src="app/public/app.factory.js"></script>
    <script src="app/public/app.directives.js"></script>
  </head>

  <body class="gray-bg">
    <div ng-view></div>
    <!-- Mainly scripts -->
    <script src="aspinia/js/jquery-2.1.1.js"></script>
    <script src="aspinia/js/bootstrap.min.js"></script>
    <script src="aspinia/js/plugins/ladda/spin.min.js"></script>
    <script src="aspinia/js/plugins/ladda/ladda.min.js"></script>
    <script src="aspinia/js/plugins/ladda/ladda.jquery.min.js"></script>

    <!-- Login  -->

    <script src="app/public/login/login.controller.js"></script>
    <script src="app/public/login/login.factory.js"></script>
  </body>

</html>
