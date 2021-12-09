/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.controller;

import com.peopleinmotion.horizonreinicioremoto.entity.Accion;
import com.peopleinmotion.horizonreinicioremoto.entity.AccionReciente;
import com.peopleinmotion.horizonreinicioremoto.entity.Banco;
import com.peopleinmotion.horizonreinicioremoto.entity.Cajero;
import com.peopleinmotion.horizonreinicioremoto.entity.Estado;
import com.peopleinmotion.horizonreinicioremoto.entity.GrupoAccion;
import com.peopleinmotion.horizonreinicioremoto.entity.Token;
import com.peopleinmotion.horizonreinicioremoto.entity.Usuario;
import com.peopleinmotion.horizonreinicioremoto.jmoordb.JmoordbContext;
import com.peopleinmotion.horizonreinicioremoto.repository.AccionRecienteRepository;
import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import com.peopleinmotion.horizonreinicioremoto.repository.AccionRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.CajeroRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.EmailConfigurationRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.EstadoRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.GrupoAccionRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.TokenRepository;
import com.peopleinmotion.horizonreinicioremoto.services.AccionRecienteServices;
import com.peopleinmotion.horizonreinicioremoto.services.EmailServices;
import com.peopleinmotion.horizonreinicioremoto.utils.DateUtil;
import java.util.Date;
import org.primefaces.PrimeFaces;

/**
 *
 * @author avbravo
 */
@Named
@ViewScoped
public class BajarPlantillaController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="field ">
    private static final long serialVersionUID = 1L;
    private Cajero cajero = new Cajero();
    Usuario user = new Usuario();
    Banco bank = new Banco();
    Accion accion = new Accion();
    AccionReciente accionReciente = new AccionReciente();
    GrupoAccion grupoAccion = new GrupoAccion();
    List<Accion> accionList = new ArrayList<>();
    Accion selectOneMenuAccionValue = new Accion();
    Estado estado = new Estado();
    private Boolean showButton = Boolean.FALSE;
    private Boolean haveAccionReciente = Boolean.TRUE;
    private String selectOneMenuCuandoValue = "ahora";
    private Date fechahoraBaja;

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="@Inject ">
    @Inject
    TokenRepository tokenRepository;

    @Inject
    EmailServices emailServices;
    @Inject
    EmailConfigurationRepository emailConfigurationRepository;

    @Inject
    AccionRecienteServices accionRecienteServices;
    @Inject
    AccionRecienteRepository accionRecienteRepository;
    @Inject
    GrupoAccionRepository grupoAccionRepository;
    @Inject
    CajeroRepository cajeroRepository;
    @Inject
    AccionRepository accionRepository;
    @Inject
    EstadoRepository estadoRepository;

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="set/get() ">

   
    
    
    
    public Date getFechahoraBaja() {
        return fechahoraBaja;
    }

    public void setFechahoraBaja(Date fechahoraBaja) {
        this.fechahoraBaja = fechahoraBaja;
    }

    public String getSelectOneMenuCuandoValue() {
        return selectOneMenuCuandoValue;
    }

    public void setSelectOneMenuCuandoValue(String selectOneMenuCuandoValue) {
        this.selectOneMenuCuandoValue = selectOneMenuCuandoValue;
    }

    public Boolean getHaveAccionReciente() {
        return haveAccionReciente;
    }

    public void setHaveAccionReciente(Boolean haveAccionReciente) {
        this.haveAccionReciente = haveAccionReciente;
    }

    public AccionReciente getAccionReciente() {
        return accionReciente;
    }

    public void setAccionReciente(AccionReciente accionReciente) {
        this.accionReciente = accionReciente;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Boolean getShowButton() {
        return showButton;
    }

    public void setShowButton(Boolean showButton) {
        this.showButton = showButton;
    }

    public Accion getAccion() {
        return accion;
    }

    public void setAccion(Accion accion) {
        this.accion = accion;
    }

    public GrupoAccion getGrupoAccion() {
        return grupoAccion;
    }

    public void setGrupoAccion(GrupoAccion grupoAccion) {
        this.grupoAccion = grupoAccion;
    }

    public List<Accion> getAccionList() {
        return accionList;
    }

    public void setAccionList(List<Accion> accionList) {
        this.accionList = accionList;
    }

    public Accion getSelectOneMenuAccionValue() {
        return selectOneMenuAccionValue;
    }

    public void setSelectOneMenuAccionValue(Accion selectOneMenuAccionValue) {
        this.selectOneMenuAccionValue = selectOneMenuAccionValue;
    }

    public void setBank(Banco bank) {
        this.bank = bank;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Banco getBank() {
        return bank;
    }

// </editor-fold>
    /**
     * Creates a new instance of CajeroAccionController
     */
    public BajarPlantillaController() {
    }

    // <editor-fold defaultstate="collapsed" desc="void init() ">
    @PostConstruct
    public void init() {
        try {
             if(JmoordbContext.get("user")==null){
                
            }else{
            fechahoraBaja = DateUtil.fechaHoraActual();
         
            selectOneMenuCuandoValue = "" ;
            user = (Usuario) JmoordbContext.get("user");
            bank = (Banco) JmoordbContext.get("banco");
            cajero = (Cajero) JmoordbContext.get("cajero");
            grupoAccion = (GrupoAccion) JmoordbContext.get("grupoAccion");

            /**
             * Buscare las acciones del grupo
             */
            accionList = new ArrayList<>();

            if (grupoAccion.getGRUPOACCIONID().equals(JsfUtil.contextToBigInteger("grupoAccionBajarPlantillaId"))) {

                accionList = accionRepository.findByGrupoAccionIdAndPredeterminado(grupoAccion, "SI");
            } else {
                JsfUtil.warningMessage("El grupoAccion debe ser BAJAR PLANTILLA para realizar las operaciones");
            }
            if (accionList == null || accionList.isEmpty()) {

                JsfUtil.warningMessage("No hay acciones para el grupo seleccionado");
            } else {
                accion = accionList.get(0);
            }
            Optional<Estado> optional = estadoRepository.findByPredeterminadoAndActivo("SI", "SI");
            if (!optional.isPresent()) {
                JsfUtil.warningMessage("No se ha encontado el estado predeterminado para asignalor a esta operacion.");
            } else {
                estado = optional.get();
                showButton = Boolean.TRUE;
            }
            findAccionReciente();
             }
        } catch (Exception e) {
            System.out.println("init() " + e.getLocalizedMessage());
            JsfUtil.errorMessage("init() " + e.getLocalizedMessage());

        }

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String onAccionChange()">
    public String onSelectOneMenuAccionChange() {
        try {

            if (selectOneMenuAccionValue == null || selectOneMenuAccionValue.getACCION() == null) {
                //No se ha seleccionado departamemto
                JmoordbContext.put("accion", selectOneMenuAccionValue);

            } else {

                JmoordbContext.put("accion", selectOneMenuAccionValue);
            }

        } catch (Exception e) {
            JsfUtil.errorMessage("onSelectOneMenuAccionChange() " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findAccionReciente()">
    /**
     * Busca la ultima accion reciente del cajero
     *
     * @return
     */
    private String findAccionReciente() {
        try {
            Optional<AccionReciente> accionRecienteOptional = accionRecienteRepository.findByBancoIdAndCajeroIdUltimaAccionReciente(bank.getBANCOID(), cajero.getCAJEROID());
            if (accionRecienteOptional.isPresent()) {
                accionReciente = accionRecienteOptional.get();
                haveAccionReciente = Boolean.TRUE;

            }
        } catch (Exception e) {
            JsfUtil.errorMessage("findAccionReciente()" + e.getLocalizedMessage());
            PrimeFaces.current().ajax().update("form:growl");

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

    // <editor-fold defaultstate="collapsed" desc="Boolean renderedByEstadoSolicitado()">
    public Boolean renderedByEstadoSolicitado() {
        return accionRecienteServices.renderedByEstadoSolicitado(accionReciente);

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="onCommandButtonCuando()">
    public String onCommandButtonCuando() {
        try {
            selectOneMenuCuandoValue="programar evento";
            if (selectOneMenuCuandoValue == null || selectOneMenuCuandoValue.isEmpty()) {
                JsfUtil.warningMessage("Indique cuando sera efecutada la acción");
                return "";
            } else {
                if (selectOneMenuCuandoValue.equals("ahora")) {
                    return sendToken();
                } else {
                    if (selectOneMenuCuandoValue.equals("programar evento")) {
                        if (fechahoraBaja == null) {
                            JsfUtil.warningMessage("Seleccione la fecha y hora");
                            return "";
                        }
                        JmoordbContext.put("fechahoraBaja", fechahoraBaja);
                        return sendToken();
                    } else {
                        JsfUtil.warningMessage("No se encontro una condición que coincida para cuando ejecutar la acción-");
                    }
                }
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("onCommandButtonCuando() " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String sendToken()">
    public String sendToken() {
        try {
            if (selectOneMenuAccionValue == null) {
                JsfUtil.warningMessage("Seleccione la acción a ejecutar..");
                return "";
            }
            Token token = new Token();
            token.setACTIVO("SI");
            token.setCODIGOTRANSACCION(JsfUtil.getUUID());
            token.setFECHAGENERACION(DateUtil.fechaHoraActual());
            token.setFECHAVENCIMIENTO(DateUtil.sumarMinutosAFecha(token.getFECHAGENERACION(), 3));
            token.setTOKEN(JsfUtil.otp(4));
            token.setUSADO("NO");
            token.setUSUARIOID(user.getUSUARIOID());
            token.setVENCIDO("NO");
            if (tokenRepository.create(token)) {

                //Envia el token al usuario
                emailServices.sendTokenToEmail(token, user);
                JmoordbContext.put("accion", selectOneMenuAccionValue);
                if (selectOneMenuCuandoValue.equals("ahora")) {
                    return "/faces/bajarplantilla_ahora.xhtml";
                } else {
                    if (selectOneMenuCuandoValue.equals("programar evento")) {
                        System.out.println("Test -->/faces/bajarplantilla_programarevento.xhtml");
                        return "/faces/bajarplantilla_programarevento.xhtml";
                    }
                }

            } else {
                JsfUtil.warningMessage("No se creo el token");
            }

        } catch (Exception e) {
            JsfUtil.errorMessage("sendToken() " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="closeDialogEdit() ">
    public String closeDialogEdit() {
        try {

            PrimeFaces.current().executeScript("PF('widgetVarEditDialog').hide()");

        } catch (Exception e) {
            JsfUtil.errorMessage("sendToken() " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean isProgramarEvento()">
    public Boolean isProgramarEvento() {
        try {
            System.out.println("Test --> selectOneMenuCuandoValue " + selectOneMenuCuandoValue);
            JsfUtil.warningMessage("Test--> selectOneMenuCuandoValue " + selectOneMenuCuandoValue);
            if (selectOneMenuCuandoValue.trim().toLowerCase().equals("programar evento")) {
                System.out.println("Test--> es programar evento");
                return Boolean.TRUE;
            }
            System.out.println("Test--> no es programar evento...");
        } catch (Exception e) {
            JsfUtil.errorMessage("isProgramarEvento() " + e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }
// </editor-fold>


}
