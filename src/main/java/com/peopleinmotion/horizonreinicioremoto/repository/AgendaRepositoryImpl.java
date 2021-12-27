/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.repository;

import com.peopleinmotion.horizonreinicioremoto.entity.Accion;
import com.peopleinmotion.horizonreinicioremoto.entity.Agenda;
import com.peopleinmotion.horizonreinicioremoto.facade.AgendaFacade;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

/**
 *
 * @author avbravo
 */
@Stateless
public class AgendaRepositoryImpl implements AgendaRepository {

    @Inject
    AgendaFacade agendaFacade;

    @Override
    public List<Agenda> findAll() {
        return agendaFacade.findAll();
    }

    @Override

    public Optional<Agenda> findByAgendaId(BigInteger AGENDAID) {
        return agendaFacade.findByAgendaId(AGENDAID);
    }

    @Override
    public Boolean create(Agenda agenda) {
        try {
            agendaFacade.create(agenda);
            
      
            return true;
        } catch (Exception e) {
            // System.out.println("create() " + e.getLocalizedMessage());
            new FacesMessage("create(() "+e.getLocalizedMessage());
        }
        return false;
    }
    @Override
    public Boolean update(Agenda agenda) {
        try {
            agendaFacade.edit(agenda);
            
      
            return true;
        } catch (Exception e) {
            // System.out.println("edit() " + e.getLocalizedMessage());
            new FacesMessage("edit(() "+e.getLocalizedMessage());
        }
        return false;
    }

    
    
    @Override
    public Optional<Agenda> findByCodigoTransaccion(String CODIGOTRANSACCION) {
       return agendaFacade.findByCodigoTransaccion(CODIGOTRANSACCION);
    }

    @Override
    public Integer countByActivo(String ACTIVO) {
      return agendaFacade.countByActivo(ACTIVO);
    }

    @Override
    public Integer countByEstadoIdAndActivo(BigInteger ESTADOID, String ACTIVO) {
        return agendaFacade.countByEstadoIdAndActivo(ESTADOID, ACTIVO);
    }

    @Override
    public Integer countByBancoIdAndEstadoIdAndActivo(BigInteger BANCOID, BigInteger ESTADOID, String ACTIVO) {
        return agendaFacade.countByBancoIdAndEstadoIdAndActivo(BANCOID, ESTADOID, ACTIVO);
    }

    @Override
    public Optional<Agenda> find(BigInteger id) {
return agendaFacade.find(id);
    }

}
