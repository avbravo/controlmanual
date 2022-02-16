/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.facade;

import com.peopleinmotion.horizonreinicioremoto.entity.AccionReciente;
import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author avbravo
 */
@Stateless
public class AccionRecienteFacade extends AbstractFacade<AccionReciente> {

    @PersistenceContext(unitName = "com.people-inmotion_horizonreinicioremotoejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccionRecienteFacade() {
        super(AccionReciente.class);
    }
// <editor-fold defaultstate="collapsed" desc="Optional<AccionReciente> find(BigInteger id)">

    public Optional<AccionReciente> find(BigInteger id) {
        try {
            Query query = em.createNamedQuery("AccionReciente.findByAccionRecienteId");
            AccionReciente accionReciente = (AccionReciente) query.setParameter("ACCIONRECIENTEID", id).getSingleResult();
            return Optional.of(accionReciente);
        } catch (Exception e) {
 System.out.println(JsfUtil.nameOfMethod() + " "+e.getLocalizedMessage());
        }
        return Optional.empty();

    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Optional<AccionReciente> findByAccionRecienteId(BigInteger ACCIONRECIENTEID)">

    public Optional<AccionReciente> findByAccionRecienteId(BigInteger ACCIONRECIENTEID) {
        try {
            Query query = em.createNamedQuery("AccionReciente.findByAccionRecienteId");
            AccionReciente accionReciente = (AccionReciente) query.setParameter("ACCIONRECIENTEID", ACCIONRECIENTEID).getSingleResult();
            return Optional.of(accionReciente);
        } catch (Exception e) {
            // System.out.println("findByAccionRecienteId() " + e.getLocalizedMessage());
        }
        return Optional.empty();

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<AccionReciente> findByBancoId(BigInteger BANCOID)">
    public List<AccionReciente> findByBancoId(BigInteger BANCOID) {
        Query query = em.createNamedQuery("AccionReciente.findByBancoId");
        return query.setParameter("BANCOID", BANCOID).getResultList();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<AccionReciente> findByCajeroId(BigInteger CAJEROID)">
    public List<AccionReciente> findByCajeroId(BigInteger CAJEROID) {
        Query query = em.createNamedQuery("AccionReciente.findByCajeroId");
        return query.setParameter("CAJEROID", CAJEROID).getResultList();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<Cajero> findByBancoIdAndCajeroId(BigInteger BANCOID, BigInteger CAJEROID)">
    public List<AccionReciente> findByBancoIdAndCajeroId(BigInteger BANCOID, BigInteger CAJEROID) {
        List<AccionReciente> list = new ArrayList<>();
        try {

            Query query = em.createQuery("SELECT a FROM AccionReciente a WHERE a.BANCOID = :BANCOID AND a.CAJEROID = :CAJEROID");
            query.setParameter("BANCOID", BANCOID);
            list = query.setParameter("CAJEROID", CAJEROID).getResultList();
        } catch (Exception e) {
              JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return list;
    }


// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<Cajero> findByBancoIdAndCajeroIdUltimaAccionReciente(BigInteger BANCOID, BigInteger CAJEROID)">

    public Optional<AccionReciente> findByBancoIdAndCajeroIdUltimaAccionReciente(BigInteger BANCOID, BigInteger CAJEROID) {

        try {

            Query query = em.createQuery("SELECT a FROM AccionReciente a WHERE a.BANCOID = :BANCOID AND a.CAJEROID = :CAJEROID ORDER BY a.AGENDAID DESC");
            query.setParameter("BANCOID", BANCOID).setParameter("CAJEROID", CAJEROID);
            query.setFirstResult(0);
            query.setMaxResults(1);
            AccionReciente accionReciente = (AccionReciente) query.getSingleResult();
            return Optional.of(accionReciente);
        } catch (Exception e) {
   //  JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return Optional.empty();
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<Cajero> findByBancoIdAndCajeroIdUltimaAccionDisponible(BigInteger BANCOID, BigInteger CAJEROID)">

    /**
     * Devuelve la ultima accion disponible que no se ha ejecutado
     * @param BANCOID
     * @param CAJEROID
     * @return 
     */
    public Optional<AccionReciente> findByBancoIdAndCajeroIdUltimaAccionDisponible(BigInteger BANCOID, BigInteger CAJEROID) {

        try {
            
            Query query = em.createQuery("SELECT a FROM AccionReciente a WHERE a.BANCOID = :BANCOID AND a.CAJEROID = :CAJEROID AND (a.ESTADOID <= 2) ORDER BY a.AGENDAID DESC");
                 query.setParameter("BANCOID", BANCOID).setParameter("CAJEROID", CAJEROID);
              query.setHint(QueryHints.REFRESH, HintValues.TRUE);
            query.setFirstResult(0);
            query.setMaxResults(1);
            //Agregado para el refresh
      
            AccionReciente accionReciente = (AccionReciente) query.getSingleResult();
            return Optional.of(accionReciente);
        } catch (Exception ex) {
           System.out.println("error() "+JsfUtil.nameOfMethod() + " " + ex.getLocalizedMessage());
//                 JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + ex.getLocalizedMessage());
        }
        return Optional.empty();
    }
// </editor-fold>
   
    



// <editor-fold defaultstate="collapsed" desc="="List<AccionReciente> findByCajeroIdAndVistoBanco( BigInteger CAJEROID, String VISTOBANCO)">

    public List<AccionReciente> findByCajeroIdAndVistoBanco(BigInteger CAJEROID, String VISTOBANCO) {
        List<AccionReciente> list = new ArrayList<>();
        try {

            Query query = em.createQuery("SELECT a FROM AccionReciente a WHERE a.CAJEROID = :CAJEROID AND a.VISTOBANCO = :VISTOBANCO ORDER BY a.AGENDAID DESC");
            query.setParameter("CAJEROID", CAJEROID);
            list = query.setParameter("VISTOBANCO", VISTOBANCO).getResultList();
        } catch (Exception e) {
       
             JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return list;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<AccionReciente> findByBancoIdAndVistoBanco(BigInteger BANCOID, String VISTOBANCO)">
    public List<AccionReciente> findByBancoIdAndVistoBanco(BigInteger BANCOID, String VISTOBANCO) {
        List<AccionReciente> list = new ArrayList<>();
        try {

            Query query = em.createQuery("SELECT a FROM AccionReciente a WHERE a.BANCOID = :BANCOID AND a.VISTOBANCO = :VISTOBANCO ORDER BY a.AGENDAID DESC");
            query.setParameter("BANCOID", BANCOID);
            list = query.setParameter("VISTOBANCO", VISTOBANCO).getResultList();
        } catch (Exception ex) {
                 JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + ex.getLocalizedMessage());
        }
        return list;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<AccionReciente> findByBancoIdAndBanco(BigInteger BANCOID, String ACTIVO)">
    public List<AccionReciente> findByBancoIdAndActivo(BigInteger BANCOID, String ACTIVO) {
        List<AccionReciente> list = new ArrayList<>();
        try {

            Query query = em.createQuery("SELECT a FROM AccionReciente a WHERE a.BANCOID = :BANCOID AND a.ACTIVO = :ACTIVO ORDER BY a.AGENDAID DESC");
            query.setParameter("BANCOID", BANCOID);
            list = query.setParameter("ACTIVO", ACTIVO).getResultList();
        } catch (Exception ex) {
              JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + ex.getLocalizedMessage());
        }
        return list;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<AccionReciente> findByBancoIdAndVistoTecnico( BigInteger BANCOID, String VISTOTENICO)">
    public List<AccionReciente> findByBancoIdAndVistoTecnico(BigInteger BANCOID, String VISTOTENICO) {
        List<AccionReciente> list = new ArrayList<>();
        try {

            Query query = em.createQuery("SELECT a FROM AccionReciente a WHERE a.BANCOID = :BANCOID AND a.VISTOTECNICO = :VISTOTECNICO ORDER BY a.AGENDAID DESC");
            query.setParameter("BANCOID", BANCOID);
            list = query.setParameter("VISTOTECNICO", VISTOTENICO).getResultList();
        } catch (Exception ex) {
                JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + ex.getLocalizedMessage());
        }
        return list;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<AccionReciente> findByCajeroIdAndVistoTecnico( BigInteger CAJEROID, String VISTOTECNICO)">

    public List<AccionReciente> findByCajeroIdAndVistoTecnico(BigInteger CAJEROID, String VISTOTECNICO) {
        List<AccionReciente> list = new ArrayList<>();
        try {

            Query query = em.createQuery("SELECT a FROM AccionReciente a WHERE a.CAJEROID = :CAJEROID AND a.VISTOBANCO = :VISTOBANCO ORDER BY a.AGENDAID DESC");
            query.setParameter("CAJEROID", CAJEROID);
            list = query.setParameter("VISTOTECNICO", VISTOTECNICO).getResultList();
        } catch (Exception ex) {
               JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + ex.getLocalizedMessage());
        }
        return list;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<AccionReciente> findBancoIdEntreFechasTypeDate(BigInteger BANCOID, Date DESDE, Date HASTA, String ACTIVO)">
   public List<AccionReciente> findBancoIdEntreFechasTypeDate(BigInteger BANCOID, Date DESDE, Date HASTA, String ACTIVO) {
        List<AccionReciente> list = new ArrayList<>();
        try {
             
            Query query = em.createQuery("SELECT a FROM AccionReciente a WHERE a.BANCOID = :BANCOID AND a.ACTIVO = :ACTIVO AND a.FECHAAGENDADA BETWEEN :DESDE AND :HASTA");
           query.setParameter("BANCOID", BANCOID);
           query.setParameter("ACTIVO", ACTIVO);
            query.setParameter("DESDE", DESDE, TemporalType.TIMESTAMP);
           query.setParameter("HASTA", HASTA, TemporalType.TIMESTAMP);
        
          list=  query.getResultList();
        } catch (Exception ex) {
          JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + ex.getLocalizedMessage());
        }
        return list;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="List<AccionReciente> findBancoIdEntreFechasTypeLocalDateTime(BigInteger BANCOID, LocalDateTime DESDE, LocalDateTime HASTA, String ACTIVO)">

    public List<AccionReciente> findBancoIdEntreFechasTypeLocalDateTime(BigInteger BANCOID, LocalDateTime DESDE, LocalDateTime HASTA, String ACTIVO) {
        List<AccionReciente> list = new ArrayList<>();
        try {
             
            Query query = em.createQuery("SELECT a FROM AccionReciente a WHERE a.BANCOID = :BANCOID AND a.ACTIVO = :ACTIVO AND a.FECHAAGENDADA BETWEEN :DESDE AND :HASTA");
           query.setParameter("BANCOID", BANCOID);
           query.setParameter("ACTIVO", ACTIVO);
            query.setParameter("DESDE", DESDE);
           query.setParameter("HASTA", HASTA);

        
          list=  query.getResultList();
        } catch (Exception ex) {
           JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + ex.getLocalizedMessage());
        }
        return list;
    }

// </editor-fold>
    

}
