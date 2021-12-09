/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.controller;

import com.peopleinmotion.horizonreinicioremoto.entity.AccionReciente;
import com.peopleinmotion.horizonreinicioremoto.entity.Banco;
import com.peopleinmotion.horizonreinicioremoto.entity.Cajero;
import com.peopleinmotion.horizonreinicioremoto.entity.GrupoAccion;
import com.peopleinmotion.horizonreinicioremoto.entity.Usuario;
import com.peopleinmotion.horizonreinicioremoto.jmoordb.JmoordbContext;
import com.peopleinmotion.horizonreinicioremoto.repository.AccionRecienteRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.GrupoAccionRepository;
import com.peopleinmotion.horizonreinicioremoto.services.AccionRecienteServices;
import com.peopleinmotion.horizonreinicioremoto.utils.DateUtil;
import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;

/**
 *
 * @author avbravo
 */
@Named
@ViewScoped
public class CajeroEncontradoController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="field ">
    private static final long serialVersionUID = 1L;
    private Cajero cajero = new Cajero();
    Usuario user = new Usuario();
    Banco bank = new Banco();
    AccionReciente accionReciente = new AccionReciente();
    List<GrupoAccion> grupoAccionList = new ArrayList<>();
    Boolean haveAccionReciente = Boolean.FALSE;
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="@Inject ">
    @Inject
    GrupoAccionRepository grupoAccionRepository;
    @Inject
    AccionRecienteRepository accionRecienteRepository;
    @Inject
    AccionRecienteServices accionRecienteServices;
   
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="set/get() ">

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
    
    
    
    public List<GrupoAccion> getGrupoAccionList() {
        return grupoAccionList;
    }

    public void setGrupoAccionList(List<GrupoAccion> grupoAccionList) {
        this.grupoAccionList = grupoAccionList;
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
    public CajeroEncontradoController() {
    }

    // <editor-fold defaultstate="collapsed" desc="init() ">
    @PostConstruct
    public void init() {
        try {
             if(JmoordbContext.get("user")==null){
                
            }else{
            haveAccionReciente = Boolean.FALSE;
            grupoAccionList = new ArrayList<>();
            user = (Usuario) JmoordbContext.get("user");
            bank = (Banco) JmoordbContext.get("banco");
            cajero = (Cajero) JmoordbContext.get("cajero");
            findAccionReciente();
         fillSelectOneMenuGrupoAccion();
             }
        } catch (Exception e) {
            JsfUtil.errorMessage("init() " + e.getLocalizedMessage());

        }

    }
    // </editor-fold>
    

    

    // <editor-fold defaultstate="collapsed" desc="fillSelectOneMenuGrupoAccion() ">
    public String fillSelectOneMenuGrupoAccion() {
        try {
            grupoAccionList = grupoAccionRepository.findAll();
        } catch (Exception e) {
            JsfUtil.errorMessage("fillSelectOneMenuGrupoAccion() " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String onCommandButtonGrupoAccion(GrupoAccion grupoAccion) ">

    /**
     * Se ejecuta cuando se selecciona un grupo de accion
     *
     * @param grupoAccion
     * @return
     */
    public String onCommandButtonGrupoAccion(GrupoAccion grupoAccion) {
        try {
            JmoordbContext.put("grupoAccion", grupoAccion);
            System.out.println("============================================");
            System.out.println("test onCommandButtonGrupoAccion() grupoAccion.getGRUPOACCION():"+grupoAccion.getGRUPOACCION());
            System.out.println("============================================");
            if (grupoAccion.getGRUPOACCIONID().equals(JsfUtil.contextToBigInteger("grupoAccionEncenderSubirPlantillaId"))) {
                return "/faces/subirplantilla.xhtml";
            }
            if (grupoAccion.getGRUPOACCIONID().equals(JsfUtil.contextToBigInteger("grupoAccionReinicioRemotoId"))) {
                System.out.println("Test --> se va a reinicio remoto");
                return "/faces/reinicioremoto.xhtml";
            }
            if (grupoAccion.getGRUPOACCIONID().equals(JsfUtil.contextToBigInteger("grupoAccionBajarPlantillaId"))) {
                System.out.println("Test --> se va a bajar plantilla");
                return "/faces/bajarplantilla.xhtml";
            }
            JsfUtil.warningMessage("No se identifico el grupo de accion para continuar esta operación");

          
        } catch (Exception e) {
            JsfUtil.errorMessage("onCommandButtonGrupoAccion() " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>
    
        // <editor-fold defaultstate="collapsed" desc="findAccionReciente()">
    /**
     *Busca la ultima accion reciente del cajero
     * @return 
     */
    private String findAccionReciente(){
        try {
            Optional<AccionReciente> accionRecienteOptional = accionRecienteRepository.findByBancoIdAndCajeroIdUltimaAccionReciente(bank.getBANCOID(), cajero.getCAJEROID());
            if(accionRecienteOptional.isPresent()){
                accionReciente = accionRecienteOptional.get();
                haveAccionReciente = Boolean.TRUE;
                    PrimeFaces.current().ajax().update("form:growl");
   
            }else{
                
                PrimeFaces.current().ajax().update("form:growl");

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

    public Boolean renderedByEstadoSolicitado(){
        return accionRecienteServices.renderedByEstadoSolicitado(accionReciente);
       
    }
    // </editor-fold>
    
}
