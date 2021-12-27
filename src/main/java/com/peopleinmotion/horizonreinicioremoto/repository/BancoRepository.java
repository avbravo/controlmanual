/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.repository;

import com.peopleinmotion.horizonreinicioremoto.entity.AccionReciente;
import com.peopleinmotion.horizonreinicioremoto.entity.Banco;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import javax.persistence.Query;

/**
 *
 * @author avbravo
 */
public interface BancoRepository {
    public List<Banco> findAll();
        public Optional<Banco> find(BigInteger id);
    public Optional<Banco> findByBancoId(BigInteger BANCOID);

    public Optional<Banco> findByEsControlAndActivo(String ESCONTROL, String ACTIVO);
    public List<Banco> findByEsControlAndActivoList(String ESCONTROL, String ACTIVO);
    public Boolean create(Banco banco);
    public Boolean update(Banco banco);
    public Boolean delete(Banco banco);
    /*
    Query
    */
    public int queryCount(Query query);
    public List<Banco> queryPagination(Query query,Integer pageNumber,Integer rowForPage) ;
    public List<Banco> queryWithOutPagination(Query query); 
    public Boolean changed(Banco banco);
    
    
}
