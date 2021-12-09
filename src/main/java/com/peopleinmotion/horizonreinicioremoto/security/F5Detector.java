/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.security;

import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 *
 * @author avbravo
 */
@Named(value = "f5Detector")
@SessionScoped
public class F5Detector implements Serializable {

    /**
     * Creates a new instance of F5Detector
     */
    public F5Detector() {
    }
    private String previousPage = null;

    public void checkF5() {
        try {
            System.out.println("Test-->  llego a F%");
             String msg = "";
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        String id = viewRoot.getViewId();
        if (previousPage != null && (previousPage.equals(id))) { 
// It's a reload event } previousPage = id; }
System.out.println("Test---> It's a reload event } previousPage = id; }");
        }else{
            System.out.println("Test--> f5 else id "+id);
        }
        } catch (Exception e) {
            System.out.println("Test--> catchF5 "+e.getLocalizedMessage());
            JsfUtil.errorMessage(JsfUtil.nameOfMethod()+ " "+e.getLocalizedMessage());
        }
       
    }
}
