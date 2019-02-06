<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="loginColumns">
	<div class="row">
		<div class="col-md-6">
			<h2 class="font-bold">Bienvenido!</h2>
			<p>
				Lorem ipsum dolor sit amet consectetur adipisicing elit. Veniam, modi? Porro temporibus nisi, 
				voluptatem quas nam corrupti ducimus nostrum est, aperiam expedita in nobis quis consequatur. 
				Eum odio mollitia ad! Saepe odit quae voluptate maiores? Facere aut earum voluptatem exercitationem 
				possimus maiores repudiandae nemo provident vel a consequuntur molestiae veniam, 
				vero beatae enim dignissimos ipsa esse. Ab in corrupti aspernatur!
			</p>
		</div>
		<div class="col-md-6">
			<div class="ibox-content">
				<h4>Ingresa usuario y contraseña</h4>
				<hr>
				<form class="m-t" name="formLogin" 
					novalidate role="form" 
					ng-submit="vm.ingresar()">
					<div class="form-group">
						<input name="username" placeholder="Usuario"
							type="text" class="form-control input-lg"
							class="form-control input-sm"
							ng-model="vm.data.user.usuario" required>
						<div class="m-t-xs" ng-show="formLogin.username.$invalid && formLogin.username.$dirty">													
							<label class="text-danger" ng-show="formLogin.username.$error.required">Ingrese usuario.</label>													
						</div>
					</div>					
					<div class="form-group">
						<input type="password" name="clave" 
							placeholder="Contraseña" class="form-control input-lg"
							class="form-control input-sm" 
							ng-model="vm.data.user.clave" required>
						<div class="m-t-xs" ng-show="formLogin.clave.$invalid && formLogin.clave.$dirty">													
							<label class="text-danger" ng-show="formLogin.clave.$error.required">Ingrese constraseña.</label>													
						</div>
					</div>
					<div class="alert alert-danger" ng-bind="vm.data.mensaje" ng-if="vm.data.invalido"></div>
					<button type="submit" 
						class="ladda-button btn btn-primary block full-width m-b"
						data-style="slide-right">Ingresar
					</button>
					<a ui-sref="recuperar">Olvidó su contraseña?</a>
				</form>
			</div>
		</div>
	</div>
</div>