/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.facade;

import com.peopleinmotion.horizonreinicioremoto.entity.Banco;
import com.peopleinmotion.horizonreinicioremoto.paginator.QuerySQL;
import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;
import java.math.BigInteger;
import java.util.ArrayList;
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
            JsfUtil.errorMessage("AbstractFacade.create()  " + e.getLocalizedMessage());
        }

    }

    public void edit(T entity) {
        try {
            getEntityManager().merge(entity);
            //Agregar esto para persistir los datos en la base de datos
            getEntityManager().flush();
            // getEntityManager().refresh(entity);
        } catch (Exception e) {
            JsfUtil.errorMessage("AbstractFacade.edit()  " + e.getLocalizedMessage());
        }

    }

    public void remove(T entity) {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
            //Agregar esto para persistir los datos en la base de datos
            getEntityManager().flush();
//        getEntityManager().refresh(entity);
        } catch (Exception e) {
            JsfUtil.errorMessage("AbstractFacade.remove()  " + e.getLocalizedMessage());
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


    // <editor-fold defaultstate="collapsed" desc="List<Banco> queryPagination(Query query)">
    public List<T> sql(QuerySQL querySQL) {
        List<T> list = new ArrayList<>();
        try {
            Query query = getEntityManager().createQuery(querySQL.getQuery());           
            list = query.getResultList();
        } catch (Exception e) {
            System.out.println(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return list;

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<Banco> pagination(Query query,Integer pageNumber,Integer rowForPage)">
    public List<T> pagination(QuerySQL querySQL, Integer pageNumber, Integer rowForPage) {
        List<T> list = new ArrayList<>();
        try {
            Query query = getEntityManager().createQuery(querySQL.getQuery());
            query.setFirstResult(pageNumber).setMaxResults(rowForPage);
            list = query.getResultList();
        } catch (Exception e) {
            System.out.println(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return list;

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="int count(Query query)">    
    public int count(QuerySQL querySQL) {
        try {
           
            Query query = getEntityManager().createQuery(querySQL.getCount());
            return ((Long) query.getSingleResult()).intValue();

        } catch (Exception e) {
            System.out.println(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return 0;

    }
    // </editor-fold>

}
