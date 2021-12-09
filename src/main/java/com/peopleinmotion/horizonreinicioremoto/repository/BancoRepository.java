/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.repository;

import com.peopleinmotion.horizonreinicioremoto.entity.Banco;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author avbravo
 */
public interface BancoRepository {
    public List<Banco> findAll();
    public Optional<Banco> findByBancoId(BigInteger BANCOID);
    public Optional<Banco> findByEsControlActivo(String ESCONTROL, String ACTIVO);
    public Boolean create(Banco banco);
    public Boolean update(Banco banco);
    public Boolean delete(Banco banco);
    
}
