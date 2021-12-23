/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.security;

import com.peopleinmotion.horizonreinicioremoto.entity.Banco;
import com.peopleinmotion.horizonreinicioremoto.entity.Usuario;
import com.peopleinmotion.horizonreinicioremoto.jmoordb.JmoordbContext;
import com.peopleinmotion.horizonreinicioremoto.repository.BancoRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.UsuarioRepository;
import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;
import java.io.IOException;
import java.io.InputStream;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author avbravo
 */
@Named
@SessionScoped
public class AccessController implements Serializable {
// <editor-fold defaultstate="collapsed" desc="fields ">

    private static final long serialVersionUID = 1L;

    Usuario usuario = new Usuario();
    private Boolean loged = false;
    private String version = "";
    private String role = "";
    private String username="";
    private String password="";
    Banco selectOneMenuBancoValue = new Banco();
    private List<Banco> bancoList = new ArrayList<>();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="inject() ">

//
    @Inject
    BancoRepository bancoRepository;
    @Inject
    UsuarioRepository usuarioRepository;
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="set/get ">

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    

    public List<Banco> getBancoList() {
        return bancoList;
    }

    public void setBancoList(List<Banco> bancoList) {
        this.bancoList = bancoList;
    }
    
    
    
    public Banco getSelectOneMenuBancoValue() {
        return selectOneMenuBancoValue;
    }

    public void setSelectOneMenuBancoValue(Banco selectOneMenuBancoValue) {
        this.selectOneMenuBancoValue = selectOneMenuBancoValue;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }

    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getLoged() {
        return loged;
    }

    public void setLoged(Boolean loged) {
        this.loged = loged;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

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
            InputStream inputStream = getClass()
                    .getClassLoader().getResourceAsStream("configuration.properties");
            Properties prop = new Properties();

            if (inputStream != null) {

                prop.load(inputStream);
                version = prop.getProperty("version");
                /**
                 * Asigna las properties al contexto
                 */
                JsfUtil.propertiesBigIntegerToContext(prop, "grupoEstadoSolicitadoId");
                JsfUtil.propertiesBigIntegerToContext(prop, "grupoEstadoEnprocesoId");
                JsfUtil.propertiesBigIntegerToContext(prop, "grupoEstadoFinalizadoId");
                JsfUtil.propertiesBigIntegerToContext(prop, "grupoEstadoNoSepuedejecutarId");
                /**
                 * GrupoAccion
                 */
                JsfUtil.propertiesBigIntegerToContext(prop, "grupoAccionBajarPlantillaId");
                JsfUtil.propertiesBigIntegerToContext(prop, "grupoAccionReinicioRemotoId");
                JsfUtil.propertiesBigIntegerToContext(prop, "grupoAccionEncenderSubirPlantillaId");
                
                
                
                /**
                 * Estado
                 * 
                 */
                JsfUtil.propertiesBigIntegerToContext(prop, "estadoEnEsperaDeEjecucionId");
                JsfUtil.propertiesBigIntegerToContext(prop, "estadoProcesandoId");
                JsfUtil.propertiesBigIntegerToContext(prop, "estadoFinalizadoId");
                
                
                
                /**
                 * Banco
                 */
                  bancoList = bancoRepository.findByEsControlAndActivoList("NO", "SI");

            } else {
                JsfUtil.errorMessage("No se puede cargar el archivo configuration.properties");
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("No se puede cargar el archivo microservices.properties");
        }

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String login()">

    public String login() {
        try {

            setLoged(Boolean.FALSE);
           
                   
                    
            if(selectOneMenuBancoValue == null || selectOneMenuBancoValue.getBANCOID()==null){
              JsfUtil.warningMessage("Seleccione un banco");
                return null;
            }

            List<Usuario> list = usuarioRepository.findByUsername(username);
            if (list == null || list.isEmpty()) {

                JsfUtil.warningMessage("Username no esta registrado");
                return null;
            }
            Usuario u = list.get(0);

            if (!JsfUtil.desencriptar(u.getPASSWORD()).equals(password)) {
                JsfUtil.successMessage("El password no valido");
                return "";
            }

            usuario = u;
            /**
             * Guardar el context
             */
            JmoordbContext.put("user", usuario);
//            JmoordbContext.put("banco", usuario.getBANCOID());
            JmoordbContext.put("banco", selectOneMenuBancoValue);

            setLoged(Boolean.TRUE);
            JsfUtil.successMessage("Bienvenido " + usuario.getNOMBRE());
            return "dashboard.xhtml";
        } catch (Exception e) {
            JsfUtil.errorMessage(e, "login()");
        }
        return "";
    }
// </editor-fold>
    
    public String loginTester() {
        try {
            setLoged(Boolean.FALSE);

            List<Usuario> list = usuarioRepository.findByUsername(usuario.getUSERNAME());
            if (list == null || list.isEmpty()) {

                JsfUtil.warningMessage("Username no esta registrado");
                return null;
            }
            Usuario u = list.get(0);
            if (!JsfUtil.desencriptar(u.getPASSWORD()).equals(usuario.getPASSWORD())) {
                JsfUtil.successMessage("Password no valido");
                return "";
            }
            usuario = u;
            /**
             * Guardar el context
             */
            JmoordbContext.put("user", usuario);

            JmoordbContext.put("banco", usuario.getBANCOID());

            setLoged(Boolean.TRUE);
            JsfUtil.successMessage("Bienvenido " + usuario.getNOMBRE());
            return "formtester.xhtml";
        } catch (Exception e) {
            JsfUtil.errorMessage(e, "login()");
        }
        return "";
    }

    // <editor-fold defaultstate="collapsed" desc="String subjectSelectionChanged() ">
    public String subjectSelectionChanged() {
        try {

            JmoordbContext.put("banco", selectOneMenuBancoValue);

        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
//      return "/faces/buscarcajero.xhtml";
    }
// </editor-fold>
}
