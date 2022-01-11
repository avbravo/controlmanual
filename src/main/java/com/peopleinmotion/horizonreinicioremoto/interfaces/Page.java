/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.interfaces;

import com.peopleinmotion.horizonreinicioremoto.jmoordb.JmoordbContext;
import com.peopleinmotion.horizonreinicioremoto.utils.ConsoleUtil;
import com.peopleinmotion.horizonreinicioremoto.utils.DateUtil;
import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;

/**
 *
 * @author avbravo
 */
public interface Page {
    // <editor-fold defaultstate="collapsed" desc="String browserEvent()">
    default  public String browserEvent(String from) {

        String pageInView = "";
        try {
            ConsoleUtil.warning("..............................................");
            ConsoleUtil.normal("Page." +JsfUtil.nameOfMethod()+ "+from "+ from+" at "+DateUtil.fechaHoraActual());
            pageInView = (String) JmoordbContext.get("pageInView");
              System.out.println("pageInView: " + pageInView);
            if(pageInView == null){
                pageInView ="";
            }else{
                
                Boolean loged= Boolean.FALSE;
               if (JmoordbContext.get("user") != null) {
                   loged= Boolean.TRUE;
               }
                  pageInView = (pageInView == null ? (loged ? "" : "login.xhtml") : pageInView);
                  
                  
            System.out.println("pageInView Changed " + pageInView);
            }
          
           ConsoleUtil.normal("........ pageInView result: "+pageInView);
           ConsoleUtil.warning("..............................................");
            return pageInView;


        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
             ConsoleUtil.error(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return pageInView;

    }
    // </editor-fold>
}
