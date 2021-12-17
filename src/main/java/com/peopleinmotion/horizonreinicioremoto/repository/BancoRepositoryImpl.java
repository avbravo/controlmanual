/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.repository;

import com.peopleinmotion.horizonreinicioremoto.entity.Accion;
import com.peopleinmotion.horizonreinicioremoto.entity.Banco;
import com.peopleinmotion.horizonreinicioremoto.facade.BancoFacade;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author avbravo
 */
@Stateless
public class BancoRepositoryImpl implements BancoRepository {

    @Inject
    BancoFacade bancoFacade;

    @Override
    public List<Banco> findAll() {
        return bancoFacade.findAll();
    }

    @Override

    public Optional<Banco> findByBancoId(BigInteger BANCOID) {
        return bancoFacade.findByBancoId(BANCOID);
    }

    // <editor-fold defaultstate="collapsed" desc="Boolean create(Banco banco) ">

    @Override
    public Boolean create(Banco banco) {
        try {
            bancoFacade.create(banco);
            return true;
        } catch (Exception e) {
            // System.out.println("create() " + e.getLocalizedMessage());
        }
        return false;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Optional<Banco> findByEsControlActivo(String ESCONTROL, String ACTIVO) ">

    @Override
    public Optional<Banco> findByEsControlAndActivo(String ESCONTROL, String ACTIVO) {
      return bancoFacade.findByEsControlAndActivo(ESCONTROL, ACTIVO);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Optional<Banco> findByEsControlActivoList(String ESCONTROL, String ACTIVO) ">

    @Override
    public List<Banco> findByEsControlAndActivoList(String ESCONTROL, String ACTIVO) {
      return bancoFacade.findByEsControlAndActivoList(ESCONTROL, ACTIVO);
    }
    // </editor-fold>

    @Override
    public Boolean update(Banco banco) {
     try {
            bancoFacade.edit(banco);
            return true;
        } catch (Exception e) {
            // System.out.println("update() " + e.getLocalizedMessage());
        }
        return false;
    }

    @Override
    public Boolean delete(Banco banco) {
          try {
            bancoFacade.remove(banco);
            return true;
        } catch (Exception e) {
            // System.out.println("update() " + e.getLocalizedMessage());
        }
        return false;
    }
}
