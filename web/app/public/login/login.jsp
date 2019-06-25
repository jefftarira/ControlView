<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="middle-box text-center loginscreen animated fadeInDown">
	<div class="container-login">
		<div>
			<div>
				<img src="aspinia/icons/newLogos/logo_transparent_background.png"
					class="image-responsive"
					alt="">
			</div>
		</div>
		<div class="hr-line-dashed m-t-xs m-b-md"></div>
		<label class="text-muted">Ingrese usuario y contraseña</label>
		<form class="m-xs"
			role="form"
			ng-submit="vm.ingresar()"
			name="frmLogin">
			<div class="form-group m-b-md">
				<input name="username"
					placeholder="Usuario"
					id="username"
					type="text"
					class="form-control"
					required=""
					ng-model="vm.data.user.usuario">
			</div>
			<div class="form-group m-b-md">
				<input type="password"
					id="clave"
					name="clave"
					placeholder="Contraseña"
					class="form-control"
					required=""
					ng-model="vm.data.user.clave">
			</div>
			<div class="alert alert-danger"
				ng-bind="vm.data.user.mensaje"
				ng-show="vm.data.user.invalido">
			</div>
			<button type="submit"
				class="ladda-button btn btn-primary block full-width m-b flex-center-center"
				data-style="slide-right">Ingresar
			</button>
			<!-- <a class="m-t"
				href="#/forgot">Olvidó su contraseña?</a> -->
		</form>
	</div>
	<div>
		<!-- <p class="m-t">
			<small>Derechos Reservados &copy; {{mcl.config.anio}} para
				<a target="_blank"
					href="http://{{mcl.config.web}}">{{mcl.config.empresa}}</a>
			</small>
		</p> -->
	</div>
</div>
