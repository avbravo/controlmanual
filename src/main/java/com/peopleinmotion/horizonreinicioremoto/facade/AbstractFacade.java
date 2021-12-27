/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.facade;

import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author avbravo
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        try {
             getEntityManager().persist(entity);
        //Agregar esto para persistir los datos en la base de datos
        getEntityManager().flush();
        getEntityManager().refresh(entity);
        } catch (Exception e) {
            JsfUtil.errorMessage("AbstractFacade.create()  "+e.getLocalizedMessage());
        }
       

    }

    public void edit(T entity) {
        try {
             getEntityManager().merge(entity);
        //Agregar esto para persistir los datos en la base de datos
        getEntityManager().flush();
       // getEntityManager().refresh(entity);
        } catch (Exception e) {
             JsfUtil.errorMessage("AbstractFacade.edit()  "+e.getLocalizedMessage());
        }
       
       
    }

    public void remove(T entity) {
        try {
              getEntityManager().remove(getEntityManager().merge(entity));
        //Agregar esto para persistir los datos en la base de datos
        getEntityManager().flush();
//        getEntityManager().refresh(entity);
        } catch (Exception e) {
               JsfUtil.errorMessage("AbstractFacade.remove()  "+e.getLocalizedMessage());
        }
      
       
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

      // <editor-fold defaultstate="collapsed" desc=" List<T> queryWithOutPagination(Query query)">
     public List<T> queryWithOutPagination(Query query) {
          return query.getResultList();
    }
      // </editor-fold>
     
      // <editor-fold defaultstate="collapsed" desc="List<T> queryPagination(Query query,Integer pageNumber,Integer rowForPage)">
     public List<T> queryPagination(Query query,Integer pageNumber,Integer rowForPage) {
         query.setFirstResult(pageNumber).setMaxResults(rowForPage);
        return query.getResultList();
    }
      // </editor-fold>
     
     
      // <editor-fold defaultstate="collapsed" desc="int queryCount(Query query)">    

       public int queryCount(Query query) {
        //Query query = em.createQuery("SELECT COUNT(a) FROM Agenda a WHERE a.ESTADOID = :ESTADOID AND a.ACTIVO = :ACTIVO");
        return ((Long) query.getSingleResult()).intValue();
    }
       // </editor-fold>
}
