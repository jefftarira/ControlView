<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container">

  <div ng-show="vm.data.resultado.id == 0">
    <div class="row wrapper border-bottom m-t-lg">
      <div class="col-xs-12 p-r-xs">
        <div class="flex-left-center">          
          <div class="p-w-xxs"><h3 class="font-bold">Complete datos iniciales de la encuesta</h3></div>           
        </div>
      </div>
    </div>
    <div class="row no-margins p-w-xs">
      <div class="col-lg-12 m-t-md no-padding">
        <div class="ibox-content">
          <form class="m-t p-w-lg" name="frIniEncuesta" novalidate role="form">
            <div class="row m-b-md">
              <div class="col-xs-4">
                <div class="form-group">
                  <label class="control-label">Nivel de estudios superior:</label>
                  <select chosen disable-search="true" name="frTitulo"
                    class="chosen-select form-control"
                    ng-model="vm.data.resultado.idTitulo"
                    ng-options="titulo.id as titulo.descripcion for titulo in vm.data.iniData.titulos"
                    required>
                  </select>
                  <div class="m-t-xs" ng-show="frIniEncuesta.frTitulo.$invalid && frIniEncuesta.frTitulo.$dirty">													
                    <label class="text-danger" ng-show="frIniEncuesta.frTitulo.$error.required">Seleccione título.</label>													
                  </div>
                </div>
              </div>
            </div>

            <div class="row m-b-md">
              <div class="col-xs-4">
                <div class="form-group">
                  <label class="control-label">¿Tiene maestria afin a la materia que dicta?</label>
                  <div>
                    <div class="radio radio-success radio-inline">
                      <input type="radio" id="radioMaestria1" name="frMaestria" 
                        ng-model="vm.data.resultado.maestria" ng-value="true" required>
                      <label for="radioMaestria1"> Si </label>
                    </div>               
                    <div class="radio radio-success radio-inline">                  
                      <input type="radio" id="radioMaestria2" name="frMaestria" 
                        ng-model="vm.data.resultado.maestria" ng-value="false" required>
                      <label for="radioMaestria2"> No </label>
                    </div>               
                  </div>                
                </div>
              </div>
            </div>

            <div class="row m-b-md">
              <div class="col-xs-4">
                <div class="form-group">
                  <label class="control-label">Seleccione un rango de edad:</label>
                  <select chosen disable-search="true" name="frEdad"
                    class="chosen-select form-control"
                    ng-model="vm.data.resultado.idRangoEdad"
                    ng-options="edad.id as edad.descripcion for edad in vm.data.iniData.edades"
                    required>
                  </select>
                  <div class="m-t-xs" ng-show="frIniEncuesta.frEdad.$invalid && frIniEncuesta.frEdad.$dirty">													
                    <label class="text-danger" ng-show="frIniEncuesta.frEdad.$error.required">Seleccione rango de edad.</label>													
                  </div>
                </div>
              </div>
            </div>

            <div class="row m-b-md">
              <div class="col-xs-4">
                <div class="form-group">
                  <label class="control-label">Sexo:</label>
                  <div>
                    <div class="radio radio-success radio-inline">
                      <input type="radio" id="radioSexo1" name="frSexo"
                        ng-model="vm.data.resultado.sexo" ng-value="true" required>
                      <label for="radioSexo1"> Hombre </label>
                    </div>               
                    <div class="radio radio-success radio-inline">                  
                      <input type="radio" id="radioSexo2" name="frSexo"
                        ng-model="vm.data.resultado.sexo" ng-value="false" required>
                      <label for="radioSexo2"> Mujer </label>
                    </div>               
                  </div>                
                </div>
              </div>
            </div>
          </form>
          <div class="flex-right-center">
            <button class="btn btn-success ladda-button" 
              data-style="slide-right" 
              ng-disabled="frIniEncuesta.$invalid" 
              ng-click="vm.saveInicialData()">Continuar
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div ng-show="vm.data.resultado.id >0 && vm.data.finPreguntas == false">
    <div class="row wrapper border-bottom m-t-lg">
      <div class="col-xs-12 p-r-xs">
        <div class="flex-left-center">        
          <div class="p-w-xxs"><h3 class="font-bold">Complete las siguientes preguntas</h3></div>        
        </div>
      </div>
    </div>
    <div class="row no-margins p-w-xs">
      <div class="col-xs-12 m-t-md no-padding">
        <div class="ibox-content">
          <form class="m-t p-w-lg" name="frEncuesta" novalidate role="form">
            <div class="row m-b-xs">
              <div class="col-xs-12">
                <div class="form-group">
                  <h2 ng-bind="vm.data.pregunta.descripcion"></h2>
                </div>
              </div>
            </div>

            <div ng-if="vm.emptyPreguntaRespuesta() && vm.emptySubPregunta()">
              <div class="p-w-lg">
                <div class="row m-b-xxs p-w-lg" ng-repeat="res in vm.data.pregunta.respuestas">
                  <div class="col-xs-4">
                    <div class="form-group no-margins">
                    </div>
                  </div>
                </div>
              </div>
              <div class="alert alert-info m-t-sm" ng-if="vm.data.pregunta.respuestas.length > 0">
                <span ng-bind="'Debes seleccionar por lo menos '"></span>
                <strong ng-bind="vm.data.pregunta.minimo"></strong>
                <span ng-if="vm.data.pregunta.minimo = 1" ng-bind="' respuesta'"></span>
                <span ng-if="vm.data.pregunta.minimo > 1" ng-bind="' respuestas'"></span>
                <span ng-bind="' y como máximo '"></span>
                <strong ng-bind="vm.data.pregunta.maximo"></strong>
                <span ng-if="vm.data.pregunta.maximo = 1" ng-bind="' respuesta.'"></span>
                <span ng-if="vm.data.pregunta.maximo > 1" ng-bind="' respuestas.'"></span>
              </div>
              <div class="m-t-md">
                <div class="row m-sm p-sm border-top flex-left-center">
                  <div class="col-xs-6">
                    <strong ng-bind="'Pregunta '+(vm.data.secuencia-1)+' de '+vm.data.totalPreguntas"></strong>
                  </div>
                  <div class="col-xs-6">
                    <div class="flex-right-center">
                      <button class="btn btn-success ladda-button" 
                        data-style="slide-right" 
                        ng-disabled="vm.checkedCount < vm.data.pregunta.minimo || vm.checkedCount > vm.data.pregunta.maximo" 
                        ng-click="vm.loadPregunta()">Continuar
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Sub Preguntas  -->
            <div ng-if="!vm.emptySubPregunta()">
              <div class="p-w-lg">                
                <div class="m-t-md">
                  <h3 ng-bind="vm.data.subPregunta.descripcion"></h3>               
                </div>
                <div class="row m-b-xxs p-w-lg" ng-repeat="res in vm.data.subPregunta.respuestas">
                  <div class="col-xs-12">
                    <div class="form-group no-margins">
                    </div>
                  </div>
                </div>
              </div>
              <div class="alert alert-info m-t-sm" ng-if="vm.data.subPregunta.respuestas.length > 0">
                <span ng-bind="'Debes seleccionar por lo menos '"></span>
                <strong ng-bind="vm.data.subPregunta.minimo"></strong>
                <span ng-if="vm.data.subPregunta.minimo = 1" ng-bind="' respuesta'"></span>
                <span ng-if="vm.data.subPregunta.minimo > 1" ng-bind="' respuestas'"></span>
                <span ng-bind="' y como máximo '"></span>
                <strong ng-bind="vm.data.subPregunta.maximo"></strong>
                <span ng-if="vm.data.subPregunta.maximo = 1" ng-bind="' respuesta.'"></span>
                <span ng-if="vm.data.subPregunta.maximo > 1" ng-bind="' respuestas.'"></span>
              </div>
              <div class="m-t-md">
                <div class="row m-sm p-sm border-top flex-left-center">
                  <div class="col-xs-6">
                    <strong ng-bind="'Pregunta '+(vm.data.secuencia-1)+' de '+vm.data.totalPreguntas"></strong>
                  </div>
                  <div class="col-xs-6">
                    <div class="flex-right-center">
                      <button class="btn btn-success ladda-button" 
                        data-style="slide-right" 
                        ng-disabled="vm.checkedCount < vm.data.subPregunta.minimo || vm.checkedCount > vm.data.subPregunta.maximo" 
                        ng-click="vm.loadSubPregunta()">Continuar
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Respueta preguntas -->
            <div ng-if="!vm.emptyPreguntaRespuesta()">
              <div class="p-w-lg">
                <div class="m-t-md">
                  <h3 ng-bind="vm.data.preguntaRespuesta.respuestaSeleccionada"></h3>                  
                </div>
                <div class="m-t-md">
                  <span class="text-muted">Según su respuesta conteste lo siguiente:</span>
                </div>
                <div class="widget gray-bg p-xs">
                  <div ng-if="vm.data.preguntaRespuesta.descripcion !== vm.data.preguntaRespuesta.respuestaSeleccionada">
                    <h3 ng-bind="vm.data.preguntaRespuesta.descripcion"></h3>                  
                  </div>
                  <div class="row m-b-xxs p-w-lg" ng-repeat="res in vm.data.preguntaRespuesta.respuestas">
                    <div class="col-xs-12">
                      <div class="form-group no-margins">
                        <div class="checkbox checkbox-primary">
                            <input id="'check{{$index}}'" type="checkbox" 
                              ng-model="res.checked"
                              ng-click="vm.checkedFun(res)"
                              ng-disabled="vm.checkedCount >= vm.data.preguntaRespuesta.maximo && !res.checked"
                              ng-value='res.id'
                              ng-checked='res.checked'>
                            <label for="'check{{$index}}'"><strong ng-bind="res.descripcion"></strong></label>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="alert alert-info m-t-sm" ng-if="vm.data.preguntaRespuesta.respuestas.length > 0">
                <span ng-bind="'Debes seleccionar por lo menos '"></span>
                <strong ng-bind="vm.data.pregunta.minimo"></strong>
                <span ng-if="vm.data.preguntaRespuesta.minimo = 1" ng-bind="' respuesta'"></span>
                <span ng-if="vm.data.preguntaRespuesta.minimo > 1" ng-bind="' respuestas'"></span>
                <span ng-bind="' y como máximo '"></span>
                <strong ng-bind="vm.data.preguntaRespuesta.maximo"></strong>
                <span ng-if="vm.data.preguntaRespuesta.maximo = 1" ng-bind="' respuesta.'"></span>
                <span ng-if="vm.data.preguntaRespuesta.maximo > 1" ng-bind="' respuestas.'"></span>
              </div>
              <div class="m-t-md">
                <div class="row m-sm p-sm border-top flex-left-center">
                  <div class="col-xs-6">
                    <strong ng-bind="'Pregunta '+(vm.data.secuencia-1)+' de '+vm.data.totalPreguntas"></strong>
                  </div>
                  <div class="col-xs-6">
                    <div class="flex-right-center">
                      <button class="btn btn-success ladda-button" 
                        data-style="slide-right" 
                        ng-disabled="vm.checkedCount < vm.data.preguntaRespuesta.minimo || vm.checkedCount > vm.data.preguntaRespuesta.maximo" 
                        ng-click="vm.loadPregunta()">Continuar
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>

          </form>

        </div>
      </div>
    </div>
  </div>

  <div class="p-lg"  ng-show="vm.data.finPreguntas == true && vm.finalReporte ==  false">
    <div class="row wrapper border-bottom m-t-lg">
      <div class="col-xs-12 p-r-xs">
        <div class="flex-center-center">          
          <div class="p-w-xxs"><h3 class="font-bold">Complete datos finales de la encuesta</h3></div>           
        </div>
      </div>
    </div>
    <div class="row no-margins p-w-lg flex-center-center">
      <div class="col-xs-8 m-t-md no-padding">
        <div class="ibox-content">
          <form class="m-t p-w-lg" name="frFinEncuesta" novalidate role="form">
            <div class="row m-b-md">
              <div class="col-xs-12">
                <div class="form-group">
                  <label class="control-label">Ingrese correo electronico institucional:</label>
                  <input type="email" class="form-control" name="frMail" 
                      ng-model="vm.data.resultado.correo" 
                      placeholder="obligatorio"
                      required>
                  <div class="m-t-xs" ng-show="frFinEncuesta.frMail.$invalid && frFinEncuesta.frMail.$dirty">													
                    <label class="text-danger" ng-show="frFinEncuesta.frMail.$error.required">Ingrese correo.</label>													
                    <label class="text-danger" ng-show="!frFinEncuesta.frMail.$error.$valid">Correo no valido.</label>	
                  </div>
                </div>
              </div>
            </div>

            <div class="row m-b-md">
              <div class="col-xs-12">
                <div class="form-group">
                  <label class="control-label">Deje su comentario u observaciones sobre la encuesta</label>
                  <textarea class="form-control input-sm form-control-resize" 
                    ng-model="vm.data.resultado.comentario" msd-elastic rows="2"
                    placeholder="opcional"
                    name="frComentario">
                  </textarea>                 
                </div>
              </div>
            </div>

            <div class="row m-b-md flex-center-center">
              <div class="col-xs-12 text-center">
                <div class="form-group">
                  <label class="control-label">HA OBTENIDO UNA CALIFICACION DEL</label>                         
                </div>
                <div class="form-group">
                  <h3 class="control-label text-danger" ng-bind="vm.data.resultado.calificacion+' %'"></h3>
                </div>
              </div>
            </div>
          </form>
          <div class="flex-center-center">
            <button class="btn btn-primary btn-block ladda-button" 
              data-style="slide-right" 
              ng-disabled="frFinEncuesta.$invalid" 
              ng-click="vm.saveFinalData()">Finalizar
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="p-lg" ng-show="vm.finalReporte == true">
    <div class="row wrapper border-bottom m-lg">
      <div class="col-xs-12 p-r-xs">
        <div class="flex-center-center">
          <div class="widget white-bg p-lg m-b-lg text-center">
              <div class="m-b-md">
                <i class="fa fa-check fa-4x text-navy"></i>  
                <h1 class="m-xs">Gracias por realizar la encuesta!</h1>  
                <div class="m-lg">
                  <h3 class="font-bold ">
                      Se ha enviado al correo electronico el resultado obtenido.
                  </h3>
                </div>
                <button class="btn btn-success btn-outline" ng-click="vm.reloadState()">
                  Realizar nueva encuesta
                </button>
              </div>
          </div>      
        </div>
      </div>
    </div>
    
  </div>

</div>