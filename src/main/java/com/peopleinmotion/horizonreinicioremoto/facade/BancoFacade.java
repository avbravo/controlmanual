/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.facade;

import com.peopleinmotion.horizonreinicioremoto.entity.Banco;
import com.peopleinmotion.horizonreinicioremoto.entity.Banco;
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
public class BancoFacade extends AbstractFacade<Banco> {

    @PersistenceContext(unitName = "com.people-inmotion_horizonreinicioremotoejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BancoFacade() {
        super(Banco.class);
    }
    // <editor-fold defaultstate="collapsed" desc="Optional<Banco> findByBancoId(BigInteger BANCOID)">


     public Optional<Banco> findByBancoId(BigInteger BANCOID) {
         try {
        Query query = em.createNamedQuery("Banco.findByBancoId");
        Banco banco = (Banco)query.setParameter("BANCOID", BANCOID).getSingleResult();
         return Optional.of(banco);
         } catch (Exception e) {
             System.out.println("findByBancoId() "+e.getLocalizedMessage());
         }
         return Optional.empty();
      
    }// </editor-fold>
     
   
         // <editor-fold defaultstate="collapsed" desc="Optional<Banco> findByEsControlActivo(String ESCONTROL, String ACTIVO) ">

     public Optional<Banco> findByEsControlActivo(String ESCONTROL, String ACTIVO) {
         try {
      Query query = em.createQuery("SELECT b FROM Banco b WHERE b.ESCONTROL = :ESCONTROL AND b.ACTIVO = :ACTIVO");
            
          
       Banco banco = (Banco)query.setParameter("ESCONTROL", ESCONTROL).setParameter("ACTIVO", ACTIVO).getSingleResult();
         return Optional.of(banco);
         } catch (Exception e) {
             System.out.println("findByEsControlActivo() "+e.getLocalizedMessage());
         }
         return Optional.empty();
      
    }
     // </editor-fold>

}
