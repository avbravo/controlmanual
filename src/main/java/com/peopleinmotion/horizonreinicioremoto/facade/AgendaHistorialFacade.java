/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.facade;

import com.peopleinmotion.horizonreinicioremoto.entity.Agenda;
import com.peopleinmotion.horizonreinicioremoto.entity.AgendaHistorial;
import java.math.BigInteger;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author avbravo
 */
@Stateless
public class AgendaHistorialFacade extends AbstractFacade<AgendaHistorial> {

    @PersistenceContext(unitName = "com.people-inmotion_horizonreinicioremotoejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AgendaHistorialFacade() {
        super(AgendaHistorial.class);
    }
    
     public Optional<AgendaHistorial> findByAgendaHistorialId(BigInteger AGENDAHISTORIALID) {
         try {
               Query query = em.createNamedQuery("Agenda.findByAgendaId");
        AgendaHistorial agendaHistorial = (AgendaHistorial)query.setParameter("AGENDAHISTORIALID", AGENDAHISTORIALID).getSingleResult();
         return Optional.of(agendaHistorial);
         } catch (Exception e) {
             // System.out.println("findByAgendaHistorialId() "+e.getLocalizedMessage());
         }
         return Optional.empty();
      
    }
    
}
