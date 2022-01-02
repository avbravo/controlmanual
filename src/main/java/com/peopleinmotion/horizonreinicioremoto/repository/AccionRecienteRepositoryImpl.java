/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.repository;


import com.peopleinmotion.horizonreinicioremoto.entity.AccionReciente;
import com.peopleinmotion.horizonreinicioremoto.facade.AccionRecienteFacade;
import com.peopleinmotion.horizonreinicioremoto.paginator.QuerySQL;
import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author avbravo
 */
@Stateless
public class AccionRecienteRepositoryImpl implements AccionRecienteRepository {

    @Inject
    AccionRecienteFacade agendaRecienteFacade;

    @Override
    public List<AccionReciente> findAll() {
        return agendaRecienteFacade.findAll();
    }

 

   

    @Override
    public Boolean create(AccionReciente accionReciente) {
        try {
            agendaRecienteFacade.create(accionReciente);
            return true;
        } catch (Exception e) {
            // System.out.println("create() " + e.getLocalizedMessage());
        }
        return false;
    }
    @Override
    public Boolean update(AccionReciente accionReciente) {
        try {
            agendaRecienteFacade.edit(accionReciente);
            return true;
        } catch (Exception e) {
            // System.out.println("create() " + e.getLocalizedMessage());
        }
        return false;
    }

    @Override
    public Optional<AccionReciente> findByAccionRecienteId(BigInteger ACCIONRECIENTEID) {
        return agendaRecienteFacade.findByAccionRecienteId(ACCIONRECIENTEID);
    }

    @Override
    public List<AccionReciente> findByBancoId(BigInteger BANCOID) {
      return agendaRecienteFacade.findByBancoId(BANCOID);
    }

    @Override
    public List<AccionReciente> findByCajeroId(BigInteger CAJEROID) {
      return agendaRecienteFacade.findByCajeroId(CAJEROID);
    }

    @Override
    public List<AccionReciente> findByBancoIdAndCajeroId(BigInteger BANCOID, BigInteger CAJEROID) {
        return agendaRecienteFacade.findByBancoIdAndCajeroId(BANCOID, CAJEROID) ;
        
    }

    @Override
    public List<AccionReciente> findByCajeroIdAndVistoBanco(BigInteger CAJEROID, String VISTOBANCO) {
        return agendaRecienteFacade.findByCajeroIdAndVistoBanco( CAJEROID,  VISTOBANCO);
    }

    @Override
    public List<AccionReciente> findByCajeroIdAndVistoTecnico(BigInteger CAJEROID, String VISTOTECNICO) {
         return agendaRecienteFacade.findByCajeroIdAndVistoTecnico(CAJEROID, VISTOTECNICO);
    }

    @Override
    public List<AccionReciente> findByBancoIdAndVistoBanco(BigInteger BANCOID, String VISTOBANCO) {
       return agendaRecienteFacade.findByBancoIdAndVistoBanco(BANCOID,VISTOBANCO);
    }

    @Override
    public List<AccionReciente> findByBancoIdAndVistoTecnico(BigInteger BANCOID, String VISTOTENICO) {
       return agendaRecienteFacade.findByBancoIdAndVistoTecnico(BANCOID,VISTOTENICO);
    }

    @Override
    public Optional<AccionReciente> findByBancoIdAndCajeroIdUltimaAccionReciente(BigInteger BANCOID, BigInteger CAJEROID) {
       return agendaRecienteFacade.findByBancoIdAndCajeroIdUltimaAccionReciente(BANCOID, CAJEROID);
    }
    @Override
    public Optional<AccionReciente> findByBancoIdAndCajeroIdUltimaAccionDisponible(BigInteger BANCOID, BigInteger CAJEROID) {
       return agendaRecienteFacade.findByBancoIdAndCajeroIdUltimaAccionDisponible(BANCOID, CAJEROID);
    }

    @Override
    public List<AccionReciente> findBancoIdEntreFechasTypeDate(BigInteger BANCOID, Date DESDE, Date HASTA, String ACTIVO) {
          return agendaRecienteFacade.findBancoIdEntreFechasTypeDate(BANCOID,DESDE, HASTA,ACTIVO);
    }

    
     @Override
    public List<AccionReciente> findBancoIdEntreFechasTypeLocalDate(BigInteger BANCOID, LocalDateTime DESDE, LocalDateTime HASTA, String ACTIVO) {
         return agendaRecienteFacade.findBancoIdEntreFechasTypeLocalDateTime(BANCOID,DESDE, HASTA,ACTIVO);
    }

    @Override
    public List<AccionReciente> findByBancoIdAndActivo(BigInteger BANCOID, String ACTIVO) {
      return agendaRecienteFacade.findByBancoIdAndActivo(BANCOID, ACTIVO);
    }

    @Override
    public Optional<AccionReciente> find(BigInteger id) {
       return agendaRecienteFacade.find(id);
    }

    // <editor-fold defaultstate="collapsed" desc="Boolean changed(Banco banco)>

    @Override
    public Boolean changed(AccionReciente accionReciente) {
        try {
            
            Optional<AccionReciente> live = agendaRecienteFacade.find(accionReciente.getACCIONRECIENTEID());
            if (!live.isPresent()) {
                return Boolean.TRUE;
            }
            String jsonLive =live.get().toJSON() ;

            String json = accionReciente.toJSON();

            if (!json.equals(jsonLive)) {
                //Otro usuario lo cambio mientras se estaba procesando
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }
    // </editor-fold>

   
    
    @Override
    public List<AccionReciente> sql(QuerySQL querySQL) {
        return agendaRecienteFacade.sql(querySQL);
    }
    @Override
    public List<AccionReciente> pagination(QuerySQL querySQL, Integer pageNumber, Integer rowForPage) {
        return agendaRecienteFacade.pagination(querySQL, pageNumber, rowForPage);
    }

    @Override
    public int count(QuerySQL querySQL) {
       return agendaRecienteFacade.count(querySQL);
    }

}
