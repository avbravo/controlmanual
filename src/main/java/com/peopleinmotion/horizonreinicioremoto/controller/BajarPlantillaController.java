/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.controller;

import com.peopleinmotion.horizonreinicioremoto.domains.MessagesForm;
import com.peopleinmotion.horizonreinicioremoto.entity.Accion;
import com.peopleinmotion.horizonreinicioremoto.entity.AccionReciente;
import com.peopleinmotion.horizonreinicioremoto.entity.Agenda;
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
import com.peopleinmotion.horizonreinicioremoto.repository.AgendaRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.CajeroRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.EmailConfigurationRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.EstadoRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.GrupoAccionRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.TokenRepository;
import com.peopleinmotion.horizonreinicioremoto.services.AccionRecienteServices;
import com.peopleinmotion.horizonreinicioremoto.services.AgendaHistorialServices;
import com.peopleinmotion.horizonreinicioremoto.services.EmailServices;
import com.peopleinmotion.horizonreinicioremoto.services.TokenServices;
import com.peopleinmotion.horizonreinicioremoto.utils.DateUtil;
import java.util.Date;
import lombok.Data;
import org.primefaces.PrimeFaces;

/**
 *
 * @author avbravo
 */
@Named
@ViewScoped
@Data
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
    private String tokenIngresado = "****";
    private Boolean tokenEnviado = Boolean.FALSE;
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
    @Inject
    AgendaRepository agendaRepository;

    @Inject
    AgendaHistorialServices agendaHistorialServices;
    @Inject
    TokenServices tokenServices;

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
            System.out.println("Test-->Init.....");
            tokenEnviado = Boolean.FALSE;
            if (JmoordbContext.get("user") == null) {

            } else {
                fechahoraBaja = DateUtil.fechaHoraActual();

                selectOneMenuCuandoValue = "";
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
            // System.out.println("init() " + e.getLocalizedMessage());
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());

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
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
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

    // <editor-fold defaultstate="collapsed" desc="onCommandButtonSendToken() ">
    public String onCommandButtonSendToken() {

        sendToken();

        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String sendToken()">
    public String sendToken() {
        try {
            System.out.println("Test--> llego a sendToken.....");
            tokenEnviado = Boolean.FALSE;
            if (selectOneMenuAccionValue == null) {
                JsfUtil.warningMessage("Seleccione la acción a ejecutar..");
                return "";
            }
            if (fechahoraBaja == null) {
                JsfUtil.warningMessage("Seleccione la fecha y hora");
                return "";
            }
            JmoordbContext.put("fechahoraBaja", fechahoraBaja);
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

                JsfUtil.successMessage("Se envio el token a su correo. Reviselo por favor");
                tokenEnviado = Boolean.TRUE;

            } else {
                JsfUtil.warningMessage("No se pudo generar el token. Repita la acción");
            }

        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
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

            if (selectOneMenuCuandoValue.trim().toLowerCase().equals("programar evento")) {

                return Boolean.TRUE;
            }

        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String onCommandButtonBajarPlantillaProgramarEvento()">
    /**
     * Guarda el evento y envia notificaciones
     *
     * @return
     */
    public String onCommandButtonBajarPlantillaProgramarEvento() {
        try {
            if (!tokenEnviado) {
                JsfUtil.warningMessage("Usted debe solicite primero un token");
                return "";
            }
            if (!validateToken()) {
                return "";
            }

            if (accionList == null || accionList.isEmpty()) {
                JsfUtil.warningMessage("No acciones para el grupo seleccionado");
            } else {

                Date fechahoraBaja = (Date) JmoordbContext.get("fechahoraBaja");
                // System.out.println("Test--> fechahoraBaja "+fechahoraBaja);
                Agenda agenda = new Agenda();
                agenda.setACTIVO("SI");
                agenda.setCODIGOTRANSACCION(JsfUtil.generateUniqueID());
                agenda.setCAJEROID(cajero.getCAJEROID());
                agenda.setCAJERO(cajero.getCAJERO());
                agenda.setBANCOID(cajero.getBANCOID().getBANCOID());
                agenda.setESTADOID(estado.getESTADOID());
                agenda.setACCIONID(accion.getACCIONID());
                agenda.setFECHA(DateUtil.getFechaHoraActual());
                agenda.setFECHAAGENDADA(fechahoraBaja);
                agenda.setFECHAEJECUCION(fechahoraBaja);
                agenda.setUSUARIOIDATIENDE(JsfUtil.toBigInteger(0));
                agenda.setUSUARIOIDSOLICITA(user.getUSUARIOID());

                if (agendaRepository.create(agenda)) {

                    Optional<Agenda> agendaOptional = agendaRepository.findByCodigoTransaccion(agenda.getCODIGOTRANSACCION());
                    if (!agendaOptional.isPresent()) {
                        JsfUtil.warningMessage("No se encontro la agenda con ese codigo de transaccion");
                    } else {

                        agendaHistorialServices.createHistorial(agendaOptional.get(), "BAJAR PLANTILLA PROGRAMAR EVENTO", user);

                        AccionReciente accionReciente = accionRecienteServices.create(agenda, bank, cajero, accion, grupoAccion, estado);
                        JmoordbContext.put("accionReciente", accionReciente);
                        /**
                         * Envio de email
                         */

                        emailServices.sendEmailToTecnicos(accionReciente, accion, user, cajero, bank);
                        MessagesForm messagesForm = new MessagesForm.Builder()
                                .id(accionReciente.getCAJERO())
                                .header("Operación Exitosa")
                                .header2("La acción se realizo exitosamente")
                                .image("atm-green01.png")
                                .libary("images")
                                .titulo("Bajar plantilla Programar evento")
                                .mensaje("Se realizo exitosamente la baja de plantilla ")
                                .returnTo("/faces/dashboard.xhtml")
                                .build();
                        JmoordbContext.put("messagesForm", messagesForm);
                        return "/faces/messagesform.xhtml";
                    }

                }

            }
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean validateToken() ">    
    public Boolean validateToken() {
        try {
            return tokenServices.validateToken(user, tokenIngresado);
        } catch (Exception e) {
            JsfUtil.errorMessage("validateToken()" + e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String remoteCommand()">
    public String remoteCommand() {
        return "";
    }
// </editor-fold>            
    // <editor-fold defaultstate="collapsed" desc="String marcarNumero() ">

    /**
     * Se usa para marcar el numero del tokem
     *
     * @param numero
     * @return
     */
    public String marcarNumero(String numero) {

        try {
            tokenIngresado = tokenServices.marcarToken(numero, tokenIngresado);

        } catch (Exception e) {
            JsfUtil.errorMessage("marcarNumero() " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold> 

}
