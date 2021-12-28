/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.browser;

import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author avbravo
 */
@Named(value = "navigatorContoller")
@ViewScoped
public class NavigatorContoller implements Serializable{

    // <editor-fold defaultstate="collapsed" desc="fields ">
        private static final long serialVersionUID = 1L;
// </editor-fold>
    /**
     * Creates a new instance of NavigatorContoller
     */
    public NavigatorContoller() {
    }
    
    public String go(String url){
        
        return url.trim();
    }
}
