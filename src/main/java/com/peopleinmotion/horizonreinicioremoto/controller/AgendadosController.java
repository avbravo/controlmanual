/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.controller;
// <editor-fold defaultstate="collapsed" desc="import ">

import com.peopleinmotion.horizonreinicioremoto.entity.AccionReciente;
import com.peopleinmotion.horizonreinicioremoto.entity.Banco;
import com.peopleinmotion.horizonreinicioremoto.entity.Cajero;
import com.peopleinmotion.horizonreinicioremoto.entity.GrupoEstado;
import com.peopleinmotion.horizonreinicioremoto.entity.Usuario;
import com.peopleinmotion.horizonreinicioremoto.interfaces.Page;
import com.peopleinmotion.horizonreinicioremoto.jmoordb.JmoordbContext;
import com.peopleinmotion.horizonreinicioremoto.paginator.Paginator;
import com.peopleinmotion.horizonreinicioremoto.repository.AccionRecienteRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.AgendaHistorialRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.AgendaRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.BancoRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.CajeroRepository;
import com.peopleinmotion.horizonreinicioremoto.services.AgendaHistorialServices;
import com.peopleinmotion.horizonreinicioremoto.services.DashboardServices;
import com.peopleinmotion.horizonreinicioremoto.utils.ConsoleUtil;
import com.peopleinmotion.horizonreinicioremoto.utils.DateUtil;
import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.SortMeta;
// </editor-fold>

/**
 *
 * @author avbravo
 */
//@Named(value = "dashboardController")
@Named
@ViewScoped
@Data
public class AgendadosController implements Serializable, Page {

// <editor-fold defaultstate="collapsed" desc="field ">
    private static final long serialVersionUID = 1L;
    private ScheduleEvent<?> event = new DefaultScheduleEvent<>();
    private Integer rowForPage = 15;
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

    private LazyDataModel<AccionReciente> lazyDataModelAccionReciente;
    String cajeroSearch = "";
    String tituloSearch = "";
    String estadoSearch = "";
    String autorizadoSearch = "";
    String queryType = "init";
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
                if (JsfUtil.contextToInteger("rowForPage") != null) {
                    rowForPage = JsfUtil.contextToInteger("rowForPage");
                }

                fillAccionRecienteList();

                queryType = "init";

                this.lazyDataModelAccionReciente = new LazyDataModel<AccionReciente>() {
                    @Override
                    public List<AccionReciente> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

                        Integer count = 0, paginas;
                        List<AccionReciente> result = new ArrayList<>();

                        switch (queryType) {
                            case "init":
                                count = cajeroRepository.countBancoIdAndActivo(banco, "SI");
                                paginas = JsfUtil.numberOfPages(count, rowForPage);

                                result = accionRecienteRepository.findBancoIdAndActivoPaginacion(banco.getBANCOID(), "SI", offset, rowForPage);
                                break;
                            case "cajero":
                                count = accionRecienteRepository.countCajeroBancoIdAndActivoLike(cajeroSearch, banco.getBANCOID(), "SI");
                                paginas = JsfUtil.numberOfPages(count, rowForPage);
                                result = accionRecienteRepository.findCajeroBancoIdAndActivoLikePaginacion(cajeroSearch, banco.getBANCOID(), "SI", 0, rowForPage);
                                break;
                            case "titulo":
                                count = accionRecienteRepository.countTituloBancoIdAndActivoLike(tituloSearch, banco.getBANCOID(), "SI");
                                paginas = JsfUtil.numberOfPages(count, rowForPage);
                                result = accionRecienteRepository.findTituloBancoIdAndActivoLikePaginacion(tituloSearch, banco.getBANCOID(), "SI", 0, rowForPage);
                                break;
                            case "estado":
                                count = accionRecienteRepository.countEstadoBancoIdAndActivoLike(estadoSearch, banco.getBANCOID(), "SI");
                                paginas = JsfUtil.numberOfPages(count, rowForPage);
                                result = accionRecienteRepository.findEstadoBancoIdAndActivoLikePaginacion(estadoSearch, banco.getBANCOID(), "SI", 0, rowForPage);
                                break;
                            case "autorizado":
                                 if(autorizadoSearch == null || autorizadoSearch.equals("")){
                                     count = accionRecienteRepository.countBancoIdAndActivo( banco.getBANCOID(), "SI");
                                paginas = JsfUtil.numberOfPages(count, rowForPage);
                                result = accionRecienteRepository.findBancoIdAndActivoPaginacion(banco.getBANCOID(), "SI", 0, rowForPage);
                                }else{
                                     count = accionRecienteRepository.countAutorizadoBancoIdAndActivoLike(autorizadoSearch, banco.getBANCOID(), "SI");
                                paginas = JsfUtil.numberOfPages(count, rowForPage);
                                result = accionRecienteRepository.findAutorizadoBancoIdAndActivoLikePaginacion(autorizadoSearch, banco.getBANCOID(), "SI", 0, rowForPage);
                                }
                                break;
                        }

//                        Integer count = accionRecienteRepository.countBancoIdAndActivo(banco.getBANCOID(), "SI");
//                        Integer paginas = JsfUtil.numberOfPages(count, rowForPage);
//
//                        List<AccionReciente> result = accionRecienteRepository.findBancoIdAndActivoPaginacion(banco.getBANCOID(), "SI", offset, rowForPage);
                        lazyDataModelAccionReciente.setRowCount(count);
//                        PrimeFaces.current().executeScript("setDataTableWithPageStart()");
                        return result;
                    }

                };
            }
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + e.getLocalizedMessage());

        }

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="fillAccionRecienteList()">
    public String fillAccionRecienteList() {
        try {
            banco = (Banco) JmoordbContext.get("banco");
            accionRecienteList = accionRecienteRepository.findByBancoIdAndActivo(banco.getBANCOID(), "SI");

        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + e.getLocalizedMessage());
        }
        return "";
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
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }

        JmoordbContext.put("pageInView", "controlmanual.xhtml");
        return "controlmanual.xhtml";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String searchByCajero()">
    public String searchByCajero() {
        try {
            queryType = "cajero";
            tituloSearch = "";
            autorizadoSearch = "";
            estadoSearch = "";
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String String searchByTitulo()">

    public String searchByTitulo() {
        try {
            queryType = "titulo";
            cajeroSearch = "";
            autorizadoSearch = "";
            estadoSearch = "";
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String String searchByEstado()">

    public String searchByEstado() {
        try {
            queryType = "estado";
            cajeroSearch = "";
            tituloSearch = "";
            autorizadoSearch = "";
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String String searchByAutorizado()">

    public String searchByAutorizado() {
        try {
            queryType = "autorizado";

            cajeroSearch = "";
            tituloSearch = "";
            estadoSearch = "";
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>

}
