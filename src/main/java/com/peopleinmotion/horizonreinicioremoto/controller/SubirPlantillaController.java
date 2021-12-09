/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.controller;

import com.peopleinmotion.horizonreinicioremoto.entity.Accion;
import com.peopleinmotion.horizonreinicioremoto.entity.AccionReciente;
import com.peopleinmotion.horizonreinicioremoto.entity.Agenda;
import com.peopleinmotion.horizonreinicioremoto.entity.Banco;
import com.peopleinmotion.horizonreinicioremoto.entity.Cajero;
import com.peopleinmotion.horizonreinicioremoto.entity.Estado;
import com.peopleinmotion.horizonreinicioremoto.entity.GrupoAccion;
import com.peopleinmotion.horizonreinicioremoto.entity.Usuario;
import com.peopleinmotion.horizonreinicioremoto.jmoordb.JmoordbContext;
import com.peopleinmotion.horizonreinicioremoto.repository.AccionRecienteRepository;
import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import com.peopleinmotion.horizonreinicioremoto.repository.AccionRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.AgendaHistorialRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.AgendaRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.BancoRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.CajeroRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.EmailConfigurationRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.EstadoRepository;

import com.peopleinmotion.horizonreinicioremoto.repository.GrupoAccionRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.UsuarioRepository;
import com.peopleinmotion.horizonreinicioremoto.services.AccionRecienteServices;
import com.peopleinmotion.horizonreinicioremoto.services.AgendaHistorialServices;
import com.peopleinmotion.horizonreinicioremoto.services.EmailServices;
import com.peopleinmotion.horizonreinicioremoto.utils.DateUtil;
import java.util.Date;
import java.util.Optional;
import org.primefaces.PrimeFaces;

/**
 *
 * @author avbravo
 */
@Named
@ViewScoped
public class SubirPlantillaController implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="field ">

    private static final long serialVersionUID = 1L;
    private Cajero cajero = new Cajero();
    Usuario user = new Usuario();
    Banco bank = new Banco();
    GrupoAccion grupoAccion = new GrupoAccion();
    List<Accion> accionList = new ArrayList<>();
    Accion selectOneMenuAccionValue = new Accion();
    Estado estado = new Estado();
    Agenda agenda = new Agenda();
    Accion accion = new Accion();
    AccionReciente accionReciente = new AccionReciente();
    Boolean showButton = Boolean.FALSE;
    Boolean haveAccionReciente = Boolean.FALSE;
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="@Inject ">
    @Inject
    AgendaHistorialServices agendaHistorialServices;

    @Inject
    AccionRecienteServices accionRecienteServices;
    @Inject
    BancoRepository bancoRepository;
    @Inject
    GrupoAccionRepository grupoAccionRepository;
    @Inject
    CajeroRepository cajeroRepository;
    @Inject
    AccionRepository accionRepository;
    @Inject
    AgendaRepository agendaRepository;
    @Inject
    EstadoRepository estadoRepository;

    @Inject
    AgendaHistorialRepository agendaHistorialRepository;
    @Inject
    EmailConfigurationRepository emailConfigurationRepository;

    @Inject
    AccionRecienteRepository accionRecienteRepository;

    @Inject
    EmailServices emailServices;
    @Inject
    UsuarioRepository usuarioRepository;

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="set/get() ">
    public AccionReciente getAccionReciente() {
        return accionReciente;
    }

    public void setAccionReciente(AccionReciente accionReciente) {
        this.accionReciente = accionReciente;
    }

    public Boolean getHaveAccionReciente() {
        return haveAccionReciente;
    }

    public void setHaveAccionReciente(Boolean haveAccionReciente) {
        this.haveAccionReciente = haveAccionReciente;
    }

    public Boolean getShowButton() {
        return showButton;
    }

    public void setShowButton(Boolean showButton) {
        this.showButton = showButton;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
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
    public SubirPlantillaController() {
    }

    // <editor-fold defaultstate="collapsed" desc="public void init()  ">

    @PostConstruct
    public void init() {
        try {
             if(JmoordbContext.get("user")==null){
                
            }else{
            showButton = Boolean.FALSE;
            user = (Usuario) JmoordbContext.get("user");
            bank = (Banco) JmoordbContext.get("banco");
            cajero = (Cajero) JmoordbContext.get("cajero");
            grupoAccion = (GrupoAccion) JmoordbContext.get("grupoAccion");

            /**
             * Buscare las acciones del grupo
             */
            accionList = new ArrayList<>();

            if (grupoAccion.getGRUPOACCIONID().equals(JsfUtil.contextToBigInteger("grupoAccionEncenderSubirPlantillaId"))) {
                accionList = accionRepository.findByGrupoAccionIdAndPredeterminado(grupoAccion, "SI");
            } else {

                JsfUtil.warningMessage("El grupoAccion debe ser ENCENDER (SUBIR PLANTILLA) para realizar las operaciones");
            }

            if (accionList == null || accionList.isEmpty()) {

                JsfUtil.warningMessage("No acciones para el grupo seleccionado");
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
                JsfUtil.infoDialog("onAccionChangee()", " Nada Seleccionado");
            } else {
                JsfUtil.infoDialog("onAccionChangee() ", selectOneMenuAccionValue.getACCION());
                System.out.println("onAccionChange " + selectOneMenuAccionValue.getACCION());
                JmoordbContext.put("accion", selectOneMenuAccionValue);
            }

        } catch (Exception e) {
            JsfUtil.errorMessage("onAccionChange() " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="onCommandButtonSubirPlantilla() ">
    /**
     * Guarda el evento y envia notificaciones
     *
     * @return
     */
    public String onCommandButtonSubirPlantilla() {
        try {

            if (accionList == null || accionList.isEmpty()) {
                JsfUtil.warningMessage("No acciones para el grupo seleccionado");
            } else {

                Agenda agenda = new Agenda();
                agenda.setACTIVO("SI");
                agenda.setCODIGOTRANSACCION(JsfUtil.generateUniqueID());
                agenda.setCAJEROID(cajero.getCAJEROID());
                agenda.setCAJERO(cajero.getCAJERO());
                agenda.setBANCOID(cajero.getBANCOID().getBANCOID());
                agenda.setESTADOID(estado.getESTADOID());
                agenda.setACCIONID(accion.getACCIONID());
                agenda.setFECHA(DateUtil.getFechaHoraActual());
                agenda.setFECHAAGENDADA(DateUtil.getFechaHoraActual());
                agenda.setFECHAEJECUCION(DateUtil.getFechaHoraActual());
                agenda.setUSUARIOIDATIENDE(JsfUtil.toBigInteger(0));
                agenda.setUSUARIOIDSOLICITA(user.getUSUARIOID());

                if (agendaRepository.create(agenda)) {

                    Optional<Agenda> agendaOptional = agendaRepository.findByCodigoTransaccion(agenda.getCODIGOTRANSACCION());
                    if (!agendaOptional.isPresent()) {
                        JsfUtil.warningMessage("No se encontro la agenda con ese codigo de transaccion");
                    } else {

                        agendaHistorialServices.createHistorial(agendaOptional.get(),"SUBIR PLANTILLA",user);

                        AccionReciente accionReciente = accionRecienteServices.create(agenda, bank, cajero, accion, grupoAccion, estado);
                        JmoordbContext.put("accionReciente", accionReciente);
                        /**
                         * Envio de email
                         */

                        emailServices.sendEmailToTecnicos(accionReciente, accion, user, cajero, bank);
                        return "/faces/operacionexitosa.xhtml";
                    }


                }

            }
        } catch (Exception e) {
            JsfUtil.errorMessage("onCommandButtonSubirPlantilla()" + e.getLocalizedMessage());
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

}
