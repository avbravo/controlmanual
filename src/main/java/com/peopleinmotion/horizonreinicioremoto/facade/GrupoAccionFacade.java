/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.facade;

import com.peopleinmotion.horizonreinicioremoto.entity.Accion;
import com.peopleinmotion.horizonreinicioremoto.entity.GrupoAccion;
import com.peopleinmotion.horizonreinicioremoto.entity.Usuario;
import java.math.BigInteger;
import java.util.List;
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
public class GrupoAccionFacade extends AbstractFacade<GrupoAccion> {

    @PersistenceContext(unitName = "com.people-inmotion_horizonreinicioremotoejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GrupoAccionFacade() {
        super(GrupoAccion.class);
    }
    
    
     public Optional<GrupoAccion> findByGrupoAccionId(BigInteger GRUPOACCIONID) {
         try {
               Query query = em.createNamedQuery("GrupoAccion.findByGrupoAcconId");
        GrupoAccion grupoAccion = (GrupoAccion)query.setParameter("GRUPOACCIONID", GRUPOACCIONID).getSingleResult();
         return Optional.of(grupoAccion);
         } catch (Exception e) {
             // System.out.println("findByGrupoAccionId() "+e.getLocalizedMessage());
         }
         return Optional.empty();
      
    }

}
