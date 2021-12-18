<?xml version='1.0' encoding='UTF-8' ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:cc="http://xmlns.jcp.org/jsf/composite"
      xmlns:p="http://primefaces.org/ui"
      xmlns:f="http://xmlns.jcp.org/jsf/core"
      xmlns:c="http://xmlns.jcp.org/jsp/jstl/core"
      xmlns:h="http://xmlns.jcp.org/jsf/html">

    <!-- INTERFACE -->
    <cc:interface>
    </cc:interface>

    <!-- IMPLEMENTATION -->
    <cc:implementation>
          <p:outputPanel id="outputPanelschedule">
          
        <div class="row borlat01">
            <div class="col-12 col-lg-12 ">
                <div class="row justify-content-center titulosec01">
                    <font class="titulosec02">Agendamientos |</font>&nbsp;Panel de Horizon
                </div>

                <p:schedule id="schedule"  
                            showHeader="true"
                            rightHeaderTemplate="month,agendaWeek,agendaDay,basicDay"
                            value="#{dashboardController.lazyEventModel}"
                            widgetVar="myschedule" timeZone="GMT-5" locale="es"
                            class="schedule-forecolor-black1"
                            >

                    <p:ajax event="eventSelect"  listener="#{dashboardController.onEventSelect}" update="eventDetails"
                            oncomplete="PF('eventDialog').show();"/>
                </p:schedule>
            </div>  
        </div>
</p:outputPanel>
        <p:dialog widgetVar="eventDialog" header="Accion Reciente" showEffect="fade" hideEffect="fade">
            <h:panelGrid id="eventDetails" columns="2" cellpadding="7">
                <p:outputLabel for="cajero" value="Cajero" styleClass="p-text-bold"/>
                <p:outputLabel id="cajero" value="#{dashboardController.accionRecienteSelected.CAJERO}" />

                <p:outputLabel for="direccion" value="Direccion" styleClass="p-text-bold"/>
                <p:outputLabel id="direccion" value="#{dashboardController.cajeroSelected.DIRECCIONCORTA}" />

                <p:outputLabel for="titulo" value="Titulo" styleClass="p-text-bold"/>
                <p:outputLabel id="titulo" value="#{dashboardController.accionRecienteSelected.TITULO}"/>

                <p:outputLabel for="mensaje" value="Mensaje" styleClass="p-text-bold"/>
                <p:outputLabel id="mensaje" value="#{dashboardController.accionRecienteSelected.MENSAJE}"/>
                
                  <p:outputLabel for="from" value="Fecha" styleClass="p-text-bold"/>
                <p:datePicker id="from" value="#{dashboardController.event.startDate}" pattern="dd/MM/yyyy"
                            
                              showTime="true" appendTo="@(body)"/>
                
                <p:outputLabel for="estado" value="Estado" styleClass="p-text-bold"/>
                <p:outputLabel id="estado" value="#{dashboardController.accionRecienteSelected.ESTADO}" />
             
            </h:panelGrid>

          
           
            <f:facet name="footer">
               <p:commandButton  oncomplete="PF('eventDialog').hide();" value="Cerrar" styleClass="p-text-bold ui-button-outlined"/>
                <p:commandButton  styleClass="p-text-bold ui-button-outlined"
                                value="Procesar"
                                update=":form:growl"
                                action="#{dashboardController.onCommandButtonSelectAccionReciente(dashboardController.accionRecienteSelected,'dashboard')}"/>
              

            </f:facet>
        </p:dialog>

    </cc:implementation>
</html>