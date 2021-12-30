/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.browser;

import com.peopleinmotion.horizonreinicioremoto.utils.DateUtil;
import javax.enterprise.context.Dependent;
import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;
import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 *
 * @author avbravo
 */
@Named
@SessionScoped
public class F5Detector implements Serializable {
  private String previousPage = null;
    /**
     * Creates a new instance of F5Detector
     */
    public F5Detector() {
    }
  

    public void checkF5() {
        try {
            System.out.println("============================================");
            System.out.println("Test-->  llego a F5: "+ DateUtil.fechaHoraActual());
            System.out.println("============================================");
            String msg = "";
            UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
            String id = viewRoot.getViewId();
            
            if (previousPage == null) {
			msg = "First page ever";
		} else if (previousPage.equals(id)) {
			msg = "F5 or reload";
		} else if (FacesContext.getCurrentInstance().isPostback()) {
			msg = "It's a postback";
		} else
			msg = "It's a navigation";
            
            System.out.println("Test--> F5 msg "+msg);
		previousPage = id;
                System.out.println("Test--> id "+id);
		FacesMessage fm = new FacesMessage(msg);
		FacesContext.getCurrentInstance().addMessage(null, fm);
            

        } catch (Exception e) {
            System.out.println("Test--> catchF5 " + e.getLocalizedMessage());
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }

    }

}
