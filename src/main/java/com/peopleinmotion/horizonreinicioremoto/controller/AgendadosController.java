/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.controller;

import com.peopleinmotion.horizonreinicioremoto.entity.AccionReciente;
import com.peopleinmotion.horizonreinicioremoto.entity.Agenda;
import com.peopleinmotion.horizonreinicioremoto.entity.Banco;
import com.peopleinmotion.horizonreinicioremoto.entity.Cajero;
import com.peopleinmotion.horizonreinicioremoto.entity.GrupoEstado;
import com.peopleinmotion.horizonreinicioremoto.entity.Usuario;
import com.peopleinmotion.horizonreinicioremoto.jmoordb.JmoordbContext;
import com.peopleinmotion.horizonreinicioremoto.paginator.Paginator;
import com.peopleinmotion.horizonreinicioremoto.repository.AccionRecienteRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.AgendaHistorialRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.AgendaRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.BancoRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.CajeroRepository;
import com.peopleinmotion.horizonreinicioremoto.services.AgendaHistorialServices;
import com.peopleinmotion.horizonreinicioremoto.services.DashboardServices;
import com.peopleinmotion.horizonreinicioremoto.utils.DateUtil;
import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.faces.component.UIData;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author avbravo
 */
//@Named(value = "dashboardController")
@Named
@ViewScoped
public class AgendadosController implements Serializable {

// <editor-fold defaultstate="collapsed" desc="field ">
    private static final long serialVersionUID = 1L;
    private ScheduleEvent<?> event = new DefaultScheduleEvent<>();
    private Integer rowForPage = 5;
    private String codigoSearch;
    private String direccionSearch;
    private Cajero cajeroSelected = new Cajero();
    AccionReciente accionRecienteSelected = new AccionReciente();
    List<Cajero> cajeroList = new ArrayList<>();
    List<Cajero> cajeroSelectedList = new ArrayList<>();
    private LazyDataModel<Cajero> lazyDataModelCajero;
    private ScheduleModel lazyEventModel;

    Usuario user = new Usuario();
    Banco banco = new Banco();
    List<Banco> bancoList = new ArrayList<>();
    List<AccionReciente> accionRecienteList = new ArrayList<>();
    List<AccionReciente> accionRecienteSelectedList = new ArrayList<>();
    
    //Totales en el dashboard por grupo
    List<GrupoEstado> grupoEstadoList = new ArrayList<>();
    private BigInteger totalSolicitado = new BigInteger("0");
    private BigInteger totalFinalizado = new BigInteger("0");
    private BigInteger totalEnProceso = new BigInteger("0");
    private BigInteger totalNoSePuedeEjecutar = new BigInteger("0");
    
    Banco selectOneMenuBancoValue = new Banco();
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="paginator ">
    Paginator paginator = new Paginator();
    Paginator paginatorOld = new Paginator();
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="injects() ">
    @Inject
    AgendaRepository agendaRepository;
    @Inject
    AgendaHistorialRepository agendaHistorialRepository;

    @Inject
    AgendaHistorialServices agendaHistorialServices;
    @Inject
    BancoRepository bancoRepository;
    @Inject
    CajeroRepository cajeroRepository;
    @Inject
    DashboardServices dashboardServices;
    @Inject
    AccionRecienteRepository accionRecienteRepository;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="set/get ">

    public List<Cajero> getCajeroSelectedList() {
        return cajeroSelectedList;
    }

    public void setCajeroSelectedList(List<Cajero> cajeroSelectedList) {
        this.cajeroSelectedList = cajeroSelectedList;
    }

    
    
    public Banco getSelectOneMenuBancoValue() {
        return selectOneMenuBancoValue;
    }

    public void setSelectOneMenuBancoValue(Banco selectOneMenuBancoValue) {
        this.selectOneMenuBancoValue = selectOneMenuBancoValue;
    }

    public List<Banco> getBancoList() {
        return bancoList;
    }

    public void setBancoList(List<Banco> bancoList) {
        this.bancoList = bancoList;
    }

    public List<AccionReciente> getAccionRecienteSelectedList() {
        return accionRecienteSelectedList;
    }

    public void setAccionRecienteSelectedList(List<AccionReciente> accionRecienteSelectedList) {
        this.accionRecienteSelectedList = accionRecienteSelectedList;
    }

    public AccionReciente getAccionRecienteSelected() {
        return accionRecienteSelected;
    }

    public void setAccionRecienteSelected(AccionReciente accionRecienteSelected) {
        this.accionRecienteSelected = accionRecienteSelected;
    }

    public ScheduleEvent<?> getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent<?> event) {
        this.event = event;
    }

   
    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public void setLazyEventModel(ScheduleModel lazyEventModel) {
        this.lazyEventModel = lazyEventModel;
    }

   

    public List<GrupoEstado> getGrupoEstadoList() {
        return grupoEstadoList;
    }

    public void setGrupoEstadoList(List<GrupoEstado> grupoEstadoList) {
        this.grupoEstadoList = grupoEstadoList;
    }

    public List<AccionReciente> getAccionRecienteList() {
        return accionRecienteList;
    }

    public void setAccionRecienteList(List<AccionReciente> accionRecienteList) {
        this.accionRecienteList = accionRecienteList;
    }

    public Cajero getCajeroSelected() {
        return cajeroSelected;
    }

    public void setCajeroSelected(Cajero cajeroSelected) {
        this.cajeroSelected = cajeroSelected;
    }

    public String getCodigoSearch() {
        return codigoSearch;
    }

    public void setCodigoSearch(String codigoSearch) {
        this.codigoSearch = codigoSearch;
    }

    public String getDireccionSearch() {
        return direccionSearch;
    }

    public void setDireccionSearch(String direccionSearch) {
        this.direccionSearch = direccionSearch;
    }

    public LazyDataModel<Cajero> getLazyDataModelCajero() {
        return lazyDataModelCajero;
    }

    public void setLazyDataModelCajero(LazyDataModel<Cajero> lazyDataModelCajero) {
        this.lazyDataModelCajero = lazyDataModelCajero;
    }

    public Integer getRowForPage() {
        return rowForPage;
    }

    public void setRowForPage(Integer rowForPage) {
        this.rowForPage = rowForPage;
    }

    public List<Cajero> getCajeroList() {
        return cajeroList;
    }

    public void setCajeroList(List<Cajero> cajeroList) {
        this.cajeroList = cajeroList;
    }

    public BigInteger getTotalSolicitado() {
        return totalSolicitado;
    }

    public void setTotalSolicitado(BigInteger totalSolicitado) {
        this.totalSolicitado = totalSolicitado;
    }

    public BigInteger getTotalFinalizado() {
        return totalFinalizado;
    }

    public void setTotalFinalizado(BigInteger totalFinalizado) {
        this.totalFinalizado = totalFinalizado;
    }

    public BigInteger getTotalEnProceso() {
        return totalEnProceso;
    }

    public void setTotalEnProceso(BigInteger totalEnProceso) {
        this.totalEnProceso = totalEnProceso;
    }

    public BigInteger getTotalNoSePuedeEjecutar() {
        return totalNoSePuedeEjecutar;
    }

    public void setTotalNoSePuedeEjecutar(BigInteger totalNoSePuedeEjecutar) {
        this.totalNoSePuedeEjecutar = totalNoSePuedeEjecutar;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    
    
    
// </editor-fold>
    /**
     * Creates a new instance of DashboadController
     */
    public AgendadosController() {
    }

    // <editor-fold defaultstate="collapsed" desc="init()">
    @PostConstruct
    public void init() {
        try {
         
            if (JmoordbContext.get("user") == null) {

            } else {
              
               
              
                /**
                 * Leer de la sesion
                 */
                user = (Usuario) JmoordbContext.get("user");
                banco = (Banco) JmoordbContext.get("banco");
                
     


      
                fillAccionRecienteList();
                
                
                
               
            }
        } catch (Exception e) {
          JsfUtil.errorMessage(JsfUtil.nameOfMethod() + e.getLocalizedMessage());

        }

    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="fillAccionRecienteList()">
    public String fillAccionRecienteList(){
        try {
             banco = (Banco) JmoordbContext.get("banco");
              accionRecienteList = accionRecienteRepository.findByBancoIdAndActivo(banco.getBANCOID(), "SI");
              // System.out.println("Test--> fillAccionRecienteList() accionRecienteList.size() "+accionRecienteList.size());

        } catch (Exception e) {
             JsfUtil.errorMessage(JsfUtil.nameOfMethod() + e.getLocalizedMessage());
        }
        return "";
    }
          
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="onCommandButtonSelectCajero ">
    public String onCommandButtonSelectCajero(Cajero cajero) {
        try {
            JmoordbContext.put("cajero", cajero);

            JsfUtil.infoDialog("Selecciono el cajero ", cajero.getCAJEROID().toString());
        } catch (Exception e) {
            JsfUtil.errorMessage("onCommandButtonSelectCajero() " + e.getLocalizedMessage());
        }

        return "/faces/cajeroencontrado.xhtml";
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="String onCommandButtonSearchCajero() ">

    public String onCommandButtonSearchCajero() {

        try {
            if (codigoSearch == null || codigoSearch.equals("")) {
                codigoSearch = "";
            } else {
                if (!codigoSearch.equals("")) {
                    direccionSearch = "";
                    paginator = new Paginator.Builder()
                            .page(1)
                            .filter("bancoCajero")
                            .build();

                    JsfUtil.warningDialog("Mensaje ", "Buscar codigo " + codigoSearch);
                } else {
                    if (direccionSearch == null || direccionSearch.equals("")) {
                        direccionSearch = "";
                        paginator = new Paginator.Builder()
                                .page(1)
                                .filter("banco")
                                .build();
                        JsfUtil.warningDialog("Mensaje ", "Ingrese un codigo o una dirección a buscar");
                        return "";
                    } else {
                        codigoSearch = "";
                        paginator = new Paginator.Builder()
                                .page(1)
                                .filter("bancoDireccion")
                                .build();
                        JsfUtil.warningDialog("Mensaje ", "Buscar direccion " + direccionSearch);
                    }
                }

            }

        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " : " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>



    // <editor-fold defaultstate="collapsed" desc="updateRow(UIData table, int index)  ">
    public void updateRow(UIData table, int index) {
//        org.omnifaces.util.Ajax.updateRow(table, index);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String showDate(Date date) ">
    public String showDate(Date date) {
        return DateUtil.showDate(date);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String showHour(Date date) ">

    public String showHour(Date date) {
        return DateUtil.showHour(date);
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Boolean renderedByEstado(Estado estado)">

    public Boolean renderedByEstadoSolicitado(Integer ESTADOID) {
        try {
            if (JsfUtil.contextToBigInteger("grupoEstadoSolicitadoId").equals(ESTADOID)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            JsfUtil.errorMessage("renderedByEstadoSolicitado() " + e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }
    // </editor-fold>

    
    // <editor-fold defaultstate="collapsed" desc="String saveIndex(Integer index) ">
    public String saveIndex(Integer index) {
        try {
            JmoordbContext.put("index", index);
        } catch (Exception e) {
            JsfUtil.errorMessage("saveIndex() " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean drawRowsAgendamiento(Integer index">

    /**
     * Indica si puede renderizar la fila
     *
     * @param index
     * @return
     */
    public Boolean drawRowsAgendamiento() {
        try {
            Integer index = (Integer) JmoordbContext.get("index");

            if (accionRecienteList == null || accionRecienteList.isEmpty() || accionRecienteList.size() == 0) {
                return Boolean.FALSE;

            }

            switch (index) {
                case 0:
                case 4:
                case 8:
                case 12:
                case 16:
                case 20:
                case 24:
                case 28:
                case 32:
                case 36:
                case 40:
                case 44:
                case 48:
                case 52:
                    // System.out.println("TEST--> es row inicial");
                    return Boolean.TRUE;
            }
        } catch (Exception e) {
            //    JsfUtil.errorMessage("drawRowsAgendamiento() " + e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String onCommandButtonSelectAccionReciente(AccionReciente accionReciente)">
    public String onCommandButtonSelectAccionReciente(AccionReciente accionReciente, String formularioretorno) {
        try {
            JmoordbContext.put("accionRecienteDashboard", accionReciente);
            JmoordbContext.put("formularioRetorno", formularioretorno);
            Optional<Cajero> cajeroOptional = cajeroRepository.findByCajeroId(accionReciente.getCAJEROID());

            if (!cajeroOptional.isPresent()) {

            } else {
                Cajero cajero = cajeroOptional.get();
                JmoordbContext.put("cajero", cajero);
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("onCommandButtonSelectCajero() " + e.getLocalizedMessage());
        }
        return "/faces/controlmanual.xhtml";
    }
// </editor-fold>

    

    // <editor-fold defaultstate="collapsed" desc="String color(String titulo)">
    public String color(String titulo) {
        try {

            switch (titulo.toLowerCase()) {
                case "encender (subir plantilla)":
                    return "orange";
                case "reinicio remoto":
                    return "#27AE60";
                case "bajar plantilla":
                    return "#CB4335";
            }
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + ": " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String filterAccionReciente(Date start, Date end) ">

    // <editor-fold defaultstate="collapsed" desc="String onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent)">
    public String onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        try {
            // System.out.println("Test--> onEventSelect");
            event = selectEvent.getObject();
            String id = event.getId();

            Optional<AccionReciente> accionRecienteOptional = accionRecienteRepository.findByAccionRecienteId(JsfUtil.toBigInteger(Integer.parseInt(id)));
            if (!accionRecienteOptional.isPresent()) {
                // System.out.println("Test--> no isPresent");
                JsfUtil.warningMessage("No se encontro el codigo de accion reciente");
                return "";
            }
            // System.out.println("Test--> si es presente");
            accionRecienteSelected = accionRecienteOptional.get();
            JmoordbContext.put("accionRecienteDashboard", accionRecienteSelected);
            Optional<Cajero> cajeroOptional = cajeroRepository.findByCajeroId(accionRecienteSelected.getCAJEROID());

            if (!cajeroOptional.isPresent()) {

            } else {

                cajeroSelected = cajeroOptional.get();
                JmoordbContext.put("cajero", cajeroSelected);
            }
            JmoordbContext.put("formularioRetorno", "dashboard");

            //
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + ": " + e.getLocalizedMessage());
        }
        return "";
//        // System.out.println("Test-->hare el saltoo......");
//        return "/faces/reagendar.xhtml";

    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="cancelarAccion() ">
    public String cancelarAccion() {
        try {
            accionRecienteSelected.setACTIVO("NO");
            // System.out.println("Test--->=================================");
            // System.out.println("Test CancelarAccion " + accionRecienteSelected.toString());
            if (accionRecienteRepository.update(accionRecienteSelected)) {
                //Actualizar la agenda
                Optional<Agenda> agendaOptional = agendaRepository.findByAgendaId(accionRecienteSelected.getAGENDAID());
                if (!agendaOptional.isPresent()) {
                    JsfUtil.warningMessage("No se encontro registros de ese agendamiento");
                    return "";
                } else {
                    Agenda agenda = agendaOptional.get();
                    agenda.setACTIVO("NO");

                    if (agendaRepository.update(agenda)) {
                        agendaHistorialServices.createHistorial(agendaOptional.get(), "CANCELAR ACCION", user);
                        JmoordbContext.put("operacionexitosaMensaje", "Cancelar Accion");
                        JmoordbContext.put("accionReciente", accionRecienteSelected);
                        return "operacionexitosa.xhtml";
                    } else {
                        JsfUtil.warningMessage("No se puede actualizar la agenda...");
                        return "";
                    }

                }
            } else {
                JsfUtil.warningMessage("No se pudo actualizar la agenda reciente");
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("cancelarAccion() " + e.getLocalizedMessage());
        }
        return "";
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String onCommnandButtonGoBuscarCajero() ">
    public String onCommnandButtonGoBuscarCajero() {
        return "faces/buscarcajero.xhtml";
    }
// </editor-fold>



 
    
    
    // <editor-fold defaultstate="collapsed" desc="remoteCommand ">
    public String remoteCommand(){
        return "";
    }
// </editor-fold>
}
