/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.controller;

import com.peopleinmotion.horizonreinicioremoto.domains.TotalesEstadoBanco;
import com.peopleinmotion.horizonreinicioremoto.entity.AccionReciente;
import com.peopleinmotion.horizonreinicioremoto.entity.Banco;
import com.peopleinmotion.horizonreinicioremoto.entity.Cajero;
import com.peopleinmotion.horizonreinicioremoto.entity.Usuario;
import com.peopleinmotion.horizonreinicioremoto.interfaces.Page;
import com.peopleinmotion.horizonreinicioremoto.jmoordb.JmoordbContext;
import com.peopleinmotion.horizonreinicioremoto.paginator.Paginator;
import com.peopleinmotion.horizonreinicioremoto.paginator.QuerySQL;
import com.peopleinmotion.horizonreinicioremoto.repository.AccionRecienteRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.AgendaHistorialRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.AgendaRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.BancoRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.CajeroRepository;
import com.peopleinmotion.horizonreinicioremoto.services.AccionRecienteServices;
import com.peopleinmotion.horizonreinicioremoto.services.AgendaHistorialServices;
import com.peopleinmotion.horizonreinicioremoto.services.DashboardServices;
import com.peopleinmotion.horizonreinicioremoto.services.TotalesEstadoBancoServices;
import com.peopleinmotion.horizonreinicioremoto.utils.ConsoleUtil;
import com.peopleinmotion.horizonreinicioremoto.utils.DateUtil;
import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author avbravo
 */
@Named
@ViewScoped
@Data
public class DashboardController implements Serializable, Page {

// <editor-fold defaultstate="collapsed" desc="field ">
    private static final long serialVersionUID = 1L;
    private ScheduleEvent<?> event = new DefaultScheduleEvent<>();
    private Integer rowForPage = 5;

    private Cajero cajeroSelected = new Cajero();
    AccionReciente accionRecienteSelected = new AccionReciente();
    // List<Cajero> cajeroList = new ArrayList<>();
    List<Cajero> cajeroSelectedList = new ArrayList<>();

    private ScheduleModel lazyEventModel;

    Usuario user = new Usuario();
    Banco banco = new Banco();
    List<Banco> bancoList = new ArrayList<>();
    List<AccionReciente> accionRecienteList = new ArrayList<>();
    List<AccionReciente> accionRecienteSelectedList = new ArrayList<>();
    List<AccionReciente> accionRecienteScheduleList = new ArrayList<>();

    private TotalesEstadoBanco totalesEstadoBanco = new TotalesEstadoBanco();

    Banco selectOneMenuBancoValue = new Banco();
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="paginator ">
    Paginator paginator = new Paginator();
    Paginator paginatorOld = new Paginator();
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="injects() ">
    @Inject
    AccionRecienteServices accionRecienteServices;
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
    @Inject
    TotalesEstadoBancoServices totalesEstadoBancoServices;
// </editor-fold>

    /**
     * Creates a new instance of DashboadController
     */
    public DashboardController() {
    }

    // <editor-fold defaultstate="collapsed" desc="init()">
    @PostConstruct
    public void init() {
        try {
            user = (Usuario) JmoordbContext.get("user");
            banco = (Banco) JmoordbContext.get("banco");
            //    cajeroList = new ArrayList<>();
            accionRecienteList = new ArrayList<>();
            accionRecienteScheduleList = new ArrayList<>();

            if (JmoordbContext.get("countViewAction") == null) {
                JmoordbContext.put("countViewAction", 0);
            }
            Integer countViewAction = Integer.parseInt(JmoordbContext.get("countViewAction").toString());
           
            lazyEventModel = new LazyScheduleModel() {

                @Override
                public void loadEvents(LocalDateTime start, LocalDateTime end) {

                }
            };

            if (countViewAction == 0) {

                QuerySQL querySQL = new QuerySQL.Builder()
                        .query("SELECT b FROM Banco b WHERE b.ESCONTROL = 'NO' AND b.ACTIVO = 'SI' ORDER BY b.BANCO ASC ")
                        .count("SELECT COUNT(b) FROM Banco b WHERE b.ESCONTROL = 'NO' AND b.ACTIVO = 'SI'")
                        .build();
                bancoList = bancoRepository.sql(querySQL);
                selectOneMenuBancoValue = banco;
              
             
                fillCarouselAccionReciente();
                calcularTotales();
                loadSchedule();
          

            } else {
               
                selectOneMenuBancoValue = banco;
            }

            countViewAction = countViewAction + 1;

            JmoordbContext.put("countViewAction", countViewAction);

        } catch (Exception e) {
            ConsoleUtil.error(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + e.getLocalizedMessage());

        }

    }

    // </editor-fold>
    @PreDestroy
    public void preDestroy() {
        try {
            Integer countViewAction = Integer.parseInt(JmoordbContext.get("countViewAction").toString());
            ConsoleUtil.warning(JsfUtil.nameOfClass() + "." + JsfUtil.nameOfMethod() + " at " + DateUtil.fechaHoraActual() + " countViewAction  ( " + countViewAction + " )");

            JmoordbContext.put("countViewAction", 0);
       
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + e.getLocalizedMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="String fillCarouselAccionReciente()">
    public String fillCarouselAccionReciente() {
        try {

            banco = (Banco) JmoordbContext.get("banco");

            String where = "(a.ESTADOID ='" + JsfUtil.contextToBigInteger("estadoProcesandoId") + "' OR  "
                    + "a.ESTADOID ='" + JsfUtil.contextToBigInteger("estadoEnEsperaDeEjecucionId") + "' )";

            QuerySQL querySQL = new QuerySQL.Builder()
                    .query("SELECT a FROM AccionReciente a WHERE a.BANCOID = '" + banco.getBANCOID() + "' AND " + where + " ORDER BY a.FECHA DESC")
                    .count("")
                    .build();

            accionRecienteList = accionRecienteRepository.sql(querySQL);

        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="onCommandButtonSelectCajero ">
    public String onCommandButtonSelectCajero(Cajero cajero) {
        try {
            ConsoleUtil.greenBackground(" onCommandButtonSelectCajero");
            JmoordbContext.put("cajero", cajero);

            JsfUtil.infoDialog("Selecciono el cajero ", cajero.getCAJEROID().toString());
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        JmoordbContext.put("pageInView", "cajeroencontrado.xhtml");
        return "cajeroencontrado.xhtml";
    }
// </editor-fold>

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
    // <editor-fold defaultstate="collapsed" desc="String showDateLocalDateTime(java.time.LocalDateTime date)">
    public String showDateLocalDateTime(java.time.LocalDateTime date) {
        return DateUtil.showDateLocalDateTime(date);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String showHourLocalDateTime(java.time.LocalDateTime date)">

    public String showHourLocalDateTime(java.time.LocalDateTime date) {
        return DateUtil.showHourLocalDateTime(date);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String calcularTotales()">    
    public String calcularTotales() {
        try {
            totalesEstadoBanco = totalesEstadoBancoServices.calcularTotalesDelBanco();

        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
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
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String saveIndex(Integer index) ">
    public String saveIndex(Integer index) {
        try {
            JmoordbContext.put("index", index);
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
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
        return dashboardServices.drawRowsAgendamiento(accionRecienteList);

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String onCommandButtonSelectAccionReciente(AccionReciente accionReciente)">
    public String onCommandButtonSelectAccionReciente(AccionReciente accionReciente, String formularioretorno) {
        try {
            dashboardServices.onCommandButtonSelectAccionReciente(accionReciente, formularioretorno);
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        JmoordbContext.put("pageInView", "controlmanual.xhtml");
        return "controlmanual.xhtml";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="void loadSchedule()">
    public void loadSchedule() {
        try {
            lazyEventModel = new LazyScheduleModel() {

                @Override
                public void loadEvents(LocalDateTime start, LocalDateTime end) {

                    Date DESDE = DateUtil.setHourToDate(DateUtil.convertLocalDateTimeToJavaDate(start), 0, 00);
                    Date HASTA = DateUtil.setHourToDate(DateUtil.convertLocalDateTimeToJavaDate(end), 23, 59);
                    accionRecienteScheduleList = accionRecienteRepository.findBancoIdEntreFechasTypeDate(banco.getBANCOID(), DESDE, HASTA, "SI");

                    if (accionRecienteScheduleList == null || accionRecienteScheduleList.isEmpty()) {
                        JsfUtil.successMessage("No hay registros de acciones recientes");
                    } else {

                        accionRecienteScheduleList.forEach((a) -> {
                            String siglas = dashboardServices.generarSiglas(a);

                            DefaultScheduleEvent event = DefaultScheduleEvent.builder()
                                    .title(a.getCAJERO() + "(" + siglas + ")")
                                    .startDate(DateUtil.convertToLocalDateTimeViaInstant(a.getFECHAAGENDADA()))
                                    .endDate(DateUtil.convertToLocalDateTimeViaInstant(a.getFECHAAGENDADA()))
                                    .description(a.getESTADO())
                                    .id(a.getACCIONRECIENTEID().toString())
                                    .borderColor(color(a.getTITULO()))
                                    .build();

                            addEvent(event);

                        });
                    }

                }
            };

        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + ": " + e.getLocalizedMessage());

        }
    }// </editor-fold>

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
    public String filterAccionReciente(BigInteger bancoId, LocalDateTime start, LocalDateTime end, String activo) {
        try {
            accionRecienteScheduleList = accionRecienteRepository.findBancoIdEntreFechasTypeLocalDate(bancoId, end, start, activo);

        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + ": " + e.getLocalizedMessage());
        }
        return "";
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent)">
    public String onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        try {

            event = selectEvent.getObject();
            String id = event.getId();

            Optional<AccionReciente> accionRecienteOptional = accionRecienteRepository.findByAccionRecienteId(JsfUtil.toBigInteger(Integer.parseInt(id)));
            if (!accionRecienteOptional.isPresent()) {

                JsfUtil.warningMessage("No se encontro el codigo de accion reciente");
                return "";
            }

            accionRecienteSelected = accionRecienteOptional.get();
            JmoordbContext.put("accionRecienteDashboard", accionRecienteSelected);
            Optional<Cajero> cajeroOptional = cajeroRepository.findByCajeroId(accionRecienteSelected.getCAJEROID());

            if (!cajeroOptional.isPresent()) {

            } else {

                cajeroSelected = cajeroOptional.get();
                JmoordbContext.put("cajero", cajeroSelected);
            }
            JmoordbContext.put("formularioRetorno", "dashboard");
	

        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + ": " + e.getLocalizedMessage());
        }
  
     return "";
     //return "";

    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String onCommnandButtonGoBuscarCajero() ">
    public String onCommnandButtonGoBuscarCajero() {
        JmoordbContext.put("pageInView", "buscarcajero.xhtml");
        return "buscarcajero.xhtml";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String selectOneMenuBancoChanged() ">
    /**
     * Cuando cambia el banco
     *
     * @return
     */
    public String selectOneMenuBancoChanged() {
        try {
           
            JmoordbContext.put("banco", selectOneMenuBancoValue);
            calcularTotales();
            fillCarouselAccionReciente();
            loadSchedule();
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="remoteCommand ">
    public String remoteCommand() {
        return "";
    }
// </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Boolean renderedByEstadoSolicitado()">
    public Boolean renderedByEstadoSolicitado() {
        return accionRecienteServices.renderedByEstadoSolicitado(accionRecienteSelected);

    }
    // </editor-fold>

}
