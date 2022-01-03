/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.browser;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author avbravo
 */
@SuppressWarnings("serial")
public class CacheControlPhaseListener   implements PhaseListener
{
    public PhaseId getPhaseId()
    {
 System.out.println("Test---> llego a getPhaseId()");
        return PhaseId.RENDER_RESPONSE;
    }

    public void afterPhase(PhaseEvent event)        
    {
          System.out.println("Test---> llego afterPhase");
    }

    public void beforePhase(PhaseEvent event)
    {
        System.out.println("Test---> llego  beforePhase");
       FacesContext facesContext = event.getFacesContext();
       HttpServletResponse response = (HttpServletResponse) facesContext
                .getExternalContext().getResponse();
       response.addHeader("Pragma", "no-cache");
       response.addHeader("Cache-Control", "no-cache");
       // Stronger according to blog comment below that references HTTP spec
       response.addHeader("Cache-Control", "no-store");
       response.addHeader("Cache-Control", "must-revalidate");
       // some date in the past
       response.addHeader("Expires", "Mon, 8 Aug 2006 10:00:00 GMT");
    }
}
