/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.security;

import com.peopleinmotion.horizonreinicioremoto.entity.Banco;
import com.peopleinmotion.horizonreinicioremoto.entity.Historial;
import com.peopleinmotion.horizonreinicioremoto.entity.Usuario;

import com.peopleinmotion.horizonreinicioremoto.jmoordb.JmoordbContext;
import com.peopleinmotion.horizonreinicioremoto.paginator.QuerySQL;
import com.peopleinmotion.horizonreinicioremoto.repository.BancoRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.HistorialRepository;
import com.peopleinmotion.horizonreinicioremoto.services.AccessServices;
import com.peopleinmotion.horizonreinicioremoto.utils.BrowserUtil;
import com.peopleinmotion.horizonreinicioremoto.utils.ConsoleUtil;
import com.peopleinmotion.horizonreinicioremoto.utils.DateUtil;
import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;

/**
 *
 * @author avbravo
 */
@Named
@SessionScoped
@Data
public class AccessController implements Serializable {
// <editor-fold defaultstate="collapsed" desc="fields ">

    private static final long serialVersionUID = 1L;

    Usuario usuario = new Usuario();
    private Boolean loged = false;
    private String version = "";
    private String role = "";
    private String username = "";
    private String password = "";
    Banco selectOneMenuBancoValue = new Banco();
    private List<Banco> bancoList = new ArrayList<>();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="inject() ">

    @Inject
    BancoRepository bancoRepository;
   
    @Inject
    AccessServices accessServices;
    
     @Inject
    HistorialRepository historialRepository;
    @Inject
     BrowserUtil browserUtil;
// </editor-fold>


    /**
     * Creates a new instance of AccessController
     */
    public AccessController() {
    }

    // <editor-fold defaultstate="collapsed" desc="init()">
    @PostConstruct
    public void init() {
        loged = false;

        try {
 ConsoleUtil.info(JsfUtil.nameOfClass() + " "+JsfUtil.nameOfMethod() + " at "+DateUtil.fechaHoraActual());
            /**
             * Lee las configuraciones iniciales
             */
            version = accessServices.loadConfigurationPropeties();

            /**
             * Banco
             */
            QuerySQL querySQL = new QuerySQL.Builder()
                    .query("SELECT b FROM Banco b WHERE b.ESCONTROL = 'NO' AND b.ACTIVO = 'SI' ORDER BY b.BANCO ASC")
                    .count("SELECT COUNT(b) FROM Banco b WHERE b.ESCONTROL = 'NO' AND b.ACTIVO = 'SI'")
                    .build();

            bancoList = bancoRepository.sql(querySQL);

        } catch (Exception e) {
              JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String login()">
    public String login() {
        try {

            setLoged(Boolean.FALSE);
            if (accessServices.validateCredentials(usuario, username, password, selectOneMenuBancoValue)) {
                setLoged(Boolean.TRUE);
                JsfUtil.successMessage("Bienvenido " + usuario.getNOMBRE());
               
                usuario = (Usuario)JmoordbContext.get("user");
             
                
                
                Historial historial = new Historial.Builder()
                        .EVENTO("Login")
                        .FECHA(DateUtil.fechaHoraActual())
                        .MODULO("AccessController")
                        .TABLA("USUARIO")
                        .CONTENIDO(usuario.toJSON())
                        .USUARIOID(usuario.getUSUARIOID())
                        .build();
                
                
           
                 
               if( !historialRepository.create(historial)){
                 
               }
      
                 
                 System.out.println("Test>>> put dashboard.xhtml....");
                 JmoordbContext.put("pageInView","dashboard.xhtml");
                        
                        return "dashboard.xhtml";
            }
           
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " "+e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>

  
    // <editor-fold defaultstate="collapsed" desc="String subjectSelectionChanged() ">

    public String subjectSelectionChanged() {
        try {

            JmoordbContext.put("banco", selectOneMenuBancoValue);

        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";

    }
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="expired()">

      public String expired() {

        return "faces/login.xhtml";
    }// </editor-fold>
      
       // <editor-fold defaultstate="collapsed" desc="int getMaxInactiveInterval()">
    public int getMaxInactiveInterval() {
        int tiempo = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
                .getSession().getMaxInactiveInterval();
  
        return tiempo;
    }
    // </editor-fold>
    
      // <editor-fold defaultstate="collapsed" desc="String inicializa()">
    public String inicializa() {

        username = "";
       
        loged = false;

        return "";
    }
    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="method() ">
    public String showPageInView(){
        try {
              String pageInView = (String) JmoordbContext.get("pageInView");
              return pageInView;
         } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
             ConsoleUtil.error(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
      return "";
       
    }
// </editor-fold>
        
 // <editor-fold defaultstate="collapsed" desc="String browserEvent()">
    public String browserEvent(String from) {

        String pageInView = "";
        try {
             ConsoleUtil.greenBackgroundWhiteText(JsfUtil.nameOfClass()+ "."+JsfUtil.nameOfMethod()+ "+from "+ from+" at "+DateUtil.fechaHoraActual());
            pageInView = (String) JmoordbContext.get("pageInView");
             ConsoleUtil.normal("-zzzzzzzzzz------------>pageInView: " + pageInView);
            if(pageInView == null){
                pageInView ="";
            }else{
                  pageInView = (pageInView == null ? (loged ? JsfUtil.goUrlValidate("/faces/dashboard.xhtml") : "") : pageInView);
            ConsoleUtil.normal("--------------->pageInView Changed " + pageInView);
            }
          
           ConsoleUtil.normal("..........> pageInView result: "+pageInView);
           
            return pageInView;

        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
             ConsoleUtil.error(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return pageInView;
    }
    // </editor-fold>
}
