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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.component.UIData;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author avbravo
 */
//@Named(value = "dashboardController")
@Named
@ViewScoped
public class TotalesEstadoBancoController implements Serializable {

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
    List<TotalesEstadoBanco> totalesEstadoBancoList = new ArrayList<>();
    List<TotalesEstadoBanco> totalesEstadoBancoSelectedList = new ArrayList<>();

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

    public List<TotalesEstadoBanco> getTotalesEstadoBancoSelectedList() {
        return totalesEstadoBancoSelectedList;
    }

    public void setTotalesEstadoBancoSelectedList(List<TotalesEstadoBanco> totalesEstadoBancoSelectedList) {
        this.totalesEstadoBancoSelectedList = totalesEstadoBancoSelectedList;
    }
    
    
    

    public List<TotalesEstadoBanco> getTotalesEstadoBancoList() {
        return totalesEstadoBancoList;
    }

    public void setTotalesEstadoBancoList(List<TotalesEstadoBanco> totalesEstadoBancoList) {
        this.totalesEstadoBancoList = totalesEstadoBancoList;
    }

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
    public TotalesEstadoBancoController() {
    }

    // <editor-fold defaultstate="collapsed" desc="init()">
    @PostConstruct
    public void init() {
        try {
            totalesEstadoBancoList = new ArrayList<>();
            if (JmoordbContext.get("user") == null) {

            } else {

                /**
                 * Leer de la sesion
                 */
                user = (Usuario) JmoordbContext.get("user");
                banco = (Banco) JmoordbContext.get("banco");

                onCommandButttonCalcularTotales();

            }
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + e.getLocalizedMessage());

        }

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

    // <editor-fold defaultstate="collapsed" desc="remoteCommand ">
    public String remoteCommand() {
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="onCommandButttonCalcularTotales() ">    
    public String onCommandButttonCalcularTotales() {
        try {
totalesEstadoBancoList = new ArrayList<>();
            List<Banco> bancoList = new ArrayList<>();
            bancoList = bancoRepository.findByEsControlAndActivoList("NO", "SI");
            if (bancoList == null || bancoList.isEmpty()) {
                JsfUtil.warningDialog("Mensaje", "No hay registros de bancos para procesar");
            } else {
                for (Banco banco : bancoList) {
                    grupoEstadoList = dashboardServices.calcularTotalGrupoEstado(banco);

                    totalSolicitado = dashboardServices.totalSolicitado(grupoEstadoList);
                    totalFinalizado = dashboardServices.totalFinalizado(grupoEstadoList);
                    totalEnProceso = dashboardServices.totalEnProceso(grupoEstadoList);
                    totalNoSePuedeEjecutar = dashboardServices.totalNoSePuedeEjecutar(grupoEstadoList);
                            TotalesEstadoBanco teb =
                                    new TotalesEstadoBanco(banco, totalSolicitado,totalFinalizado, totalEnProceso, totalNoSePuedeEjecutar);
                       totalesEstadoBancoList.add(teb);
                }

            }

        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>
}
