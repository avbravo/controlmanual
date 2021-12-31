/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.peopleinmotion.horizonreinicioremoto.entity.Banco;
import com.peopleinmotion.horizonreinicioremoto.entity.Historial;
import com.peopleinmotion.horizonreinicioremoto.entity.Usuario;

import com.peopleinmotion.horizonreinicioremoto.jmoordb.JmoordbContext;
import com.peopleinmotion.horizonreinicioremoto.paginator.QuerySQL;
import com.peopleinmotion.horizonreinicioremoto.repository.BancoRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.HistorialRepository;
import com.peopleinmotion.horizonreinicioremoto.services.AccessServices;
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
            System.out.println(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
            JsfUtil.errorMessage("No se puede cargar el archivo microservices.properties");
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
}
