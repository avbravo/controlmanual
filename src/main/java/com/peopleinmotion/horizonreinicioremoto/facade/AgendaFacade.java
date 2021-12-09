/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.facade;

import com.peopleinmotion.horizonreinicioremoto.entity.Agenda;
import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;
import java.math.BigInteger;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 *
 * @author avbravo
 */
@Stateless
public class AgendaFacade extends AbstractFacade<Agenda> {

    @PersistenceContext(unitName = "com.people-inmotion_horizonreinicioremotoejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AgendaFacade() {
        super(Agenda.class);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Optional<Agenda> findByAgendaId(BigInteger AGENDAID) ">

     public Optional<Agenda> findByAgendaId(BigInteger AGENDAID) {
         try {
               Query query = em.createNamedQuery("Agenda.findByAgendaId");
        Agenda agenda = (Agenda)query.setParameter("AGENDAID", AGENDAID).getSingleResult();
         return Optional.of(agenda);
         } catch (Exception e) {
             System.out.println("findByAgendaId() "+e.getLocalizedMessage());
         }
         return Optional.empty();
      
    }
     // </editor-fold>
     // <editor-fold defaultstate="collapsed" desc="Optional<Agenda> findByCodigoTransaccion(String  CODIGOTRANSACCION)">


     public Optional<Agenda> findByCodigoTransaccion(String  CODIGOTRANSACCION) {
         try {
               Query query = em.createNamedQuery("Agenda.findByCodigoTransaccion");
        Agenda agenda = (Agenda)query.setParameter("CODIGOTRANSACCION", CODIGOTRANSACCION).getSingleResult();
         return Optional.of(agenda);
         } catch (Exception e) {
             System.out.println("findByCodigoTransaccion() "+e.getLocalizedMessage());
         }
         return Optional.empty();
      
    }
     // </editor-fold>
     
     // <editor-fold defaultstate="collapsed" desc="Long countByActivo(String ACTIVO)">    

       public int countByActivo(String ACTIVO) {
        Query query = em.createQuery("SELECT COUNT(a) FROM Agenda a WHERE a.ACTIVO = :ACTIVO");
        return ((Long) query.setParameter("ACTIVO", ACTIVO).getSingleResult()).intValue();
    }
       // </editor-fold>
       
       // <editor-fold defaultstate="collapsed" desc="Long countByEstadoIdAndActivo(BigInteger ESTADOID,String ACTIVO) ">    

       public int countByEstadoIdAndActivo(BigInteger ESTADOID,String ACTIVO) {
        Query query = em.createQuery("SELECT COUNT(a) FROM Agenda a WHERE a.ESTADOID = :ESTADOID AND a.ACTIVO = :ACTIVO");
        return ((Long) query.setParameter("ESTADOID", ESTADOID).setParameter("ACTIVO", ACTIVO).getSingleResult()).intValue();
    }
       // </editor-fold>
       // <editor-fold defaultstate="collapsed" desc="Long countByBancoIdAndEstadoIdAndActivo(BigInteger BANCOID,BigInteger ESTADOID,String ACTIVO)">    

       public int countByBancoIdAndEstadoIdAndActivo(BigInteger BANCOID,BigInteger ESTADOID,String ACTIVO) {
        Query query = em.createQuery("SELECT COUNT(a) FROM Agenda a WHERE a.BANCOID = :BANCOID AND a.ESTADOID = :ESTADOID AND a.ACTIVO = :ACTIVO");
        return ((Long) query.setParameter("BANCOID", BANCOID).setParameter("ESTADOID", ESTADOID).setParameter("ACTIVO", ACTIVO).getSingleResult()).intValue();
        
  
    }
       // </editor-fold>
}
