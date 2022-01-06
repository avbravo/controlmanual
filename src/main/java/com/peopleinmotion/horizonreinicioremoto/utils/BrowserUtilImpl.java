/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.utils;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author avbravo
 */

public class BrowserUtilImpl implements BrowserUtil {

    // <editor-fold defaultstate="collapsed" desc="String url() ">

    @Override
    public String url() {
        String value = "";
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            value = request.getRequestURL().toString();

        } catch (Exception e) {
            System.out.println(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return value;
    }
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="String uri() ">

    @Override
    public String uri() {
        String value = "";
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            value = request.getRequestURI();
        } catch (Exception e) {
            System.out.println(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return value;
    }// </editor-fold>

    
    // <editor-fold defaultstate="collapsed" desc="String uri(String textToRemove)">

    @Override
    public String uri(String textToRemove) {
       String value = "";
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            value = request.getRequestURI();
            if(value.indexOf(textToRemove) == -1){
                
            }else{
                value = value.substring(textToRemove.length(),value.length());
            }
         
        } catch (Exception e) {
            System.out.println(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return value;
    }
// </editor-fold>
}
