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
import lombok.Data;
import org.primefaces.PrimeFaces;

/**
 *
 * @author avbravo
 */
@Named
@ViewScoped
@Data
public class CajeroEncontradoController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="field ">
    private static final long serialVersionUID = 1L;
    private Cajero cajero = new Cajero();
    Usuario user = new Usuario();
    Banco bank = new Banco();
    AccionReciente accionReciente = new AccionReciente();
    List<GrupoAccion> grupoAccionList = new ArrayList<>();
    private GrupoAccion grupoAccionBajarPlantilla = new GrupoAccion();
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
            findAccionDisponible();
            if(accionReciente == null || accionReciente.getACCIONID() == null){
            
            }
            
         fillSelectOneMenuGrupoAccionBajarPlantilla();
             }
        } catch (Exception e) {
            JsfUtil.errorMessage("init() " + e.getLocalizedMessage());

        }

    }
    // </editor-fold>
    

    

    // <editor-fold defaultstate="collapsed" desc="fillSelectOneMenuGrupoAccion() ">
    public String fillSelectOneMenuGrupoAccionBajarPlantilla() {
        try {
             grupoAccionList = new ArrayList<>();
//            List<GrupoAccion> list= grupoAccionRepository.fºindAll();
           Optional<GrupoAccion> optional= grupoAccionRepository.findByGrupoAccionId(JsfUtil.contextToBigInteger("grupoAccionBajarPlantillaId"));
           if(optional.isPresent()){
               grupoAccionBajarPlantilla = optional.get();
           }else{
               JsfUtil.warningMessage("No se encontro el grupo de Accion para bajar plantilla");
           }
           
//            if(list == null || list.isEmpty()){}
//            else{
//                for(GrupoAccion ga:list){
//                    if(ga.getGRUPOACCIONID().equals(JsfUtil.contextToBigInteger("grupoAccionBajarPlantillaId"))){
//                        grupoAccionList.add(ga);
//                    }
//                }
//                 
//            }
            
            
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod()+  " " + e.getLocalizedMessage());
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
            
            if (grupoAccion.getGRUPOACCIONID().equals(JsfUtil.contextToBigInteger("grupoAccionEncenderSubirPlantillaId"))) {
                JmoordbContext.put("pageInView", "/faces/subirplantilla.xhtml");
                return "/faces/subirplantilla.xhtml";
            }
            if (grupoAccion.getGRUPOACCIONID().equals(JsfUtil.contextToBigInteger("grupoAccionReinicioRemotoId"))) {
              JmoordbContext.put("pageInView", "/faces/reinicioremoto.xhtml");   
                return "/faces/reinicioremoto.xhtml";
            }
            if (grupoAccion.getGRUPOACCIONID().equals(JsfUtil.contextToBigInteger("grupoAccionBajarPlantillaId"))) {
                  JmoordbContext.put("pageInView", "/faces/bajarplantilla.xhtml"); 
                return "/faces/bajarplantilla.xhtml";
            }
            JsfUtil.warningMessage("No se identifico el grupo de accion para continuar esta operación");

          
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod()+ " "+ e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String onCommandButtonGrupoAccionBajarPlantilla() ">

    /**
     * Se ejecuta cuando se selecciona un grupo de accion
     *
     * @param grupoAccion
     * @return
     */
    public String onCommandButtonGrupoAccionBajarPlantilla() {
        try {
            JmoordbContext.put("grupoAccion", grupoAccionBajarPlantilla);
            JmoordbContext.put("pageInView", "/faces/bajarplantilla.xhtml");
             return "/faces/bajarplantilla.xhtml";
           
          
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod()+ " "+ e.getLocalizedMessage());
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
             JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
             PrimeFaces.current().ajax().update("form:growl");
             
        }
        return "";
    }
// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="String findAccionDisponible()">
    /**
     *Busca la ultima accion reciente del cajero
     * @return 
     */
    private String findAccionDisponible(){
        try {
          
            Optional<AccionReciente> accionRecienteOptional = accionRecienteRepository.findByBancoIdAndCajeroIdUltimaAccionDisponible(bank.getBANCOID(), cajero.getCAJEROID());
            if(accionRecienteOptional.isPresent()){
                accionReciente = accionRecienteOptional.get();
                haveAccionReciente = Boolean.TRUE;
         
              
   
            }else{
                
              
            }
        } catch (Exception e) {
            System.out.println("Error() "+ JsfUtil.nameOfMethod() + " "+e.getLocalizedMessage());
             JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
             PrimeFaces.current().ajax().update(":form:growl");
             
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
    
    public String onCommandButtonIrControlManual(){
        try {
            JmoordbContext.put("accionRecienteDashboard",accionReciente);
            JmoordbContext.put("formularioRetorno","cajeroencontrado");
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " "+e.getLocalizedMessage());
        }
        JmoordbContext.put("pageInView", "/faces/controlmanual.xhtml");
        return "controlmanual.xhtml";
    }
    
}
