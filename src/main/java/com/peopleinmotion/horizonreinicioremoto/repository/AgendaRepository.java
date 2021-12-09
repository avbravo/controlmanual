/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.repository;

import com.peopleinmotion.horizonreinicioremoto.entity.Agenda;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author avbravo
 */
public interface AgendaRepository {

    public List<Agenda> findAll();

    public Optional<Agenda> findByAgendaId(BigInteger AGENDAID);

    public Boolean create(Agenda agenda);
     public Boolean update(Agenda agenda);

    public Optional<Agenda> findByCodigoTransaccion(String CODIGOTRANSACCION);

    public Integer countByActivo(String ACTIVO);

    public Integer countByEstadoIdAndActivo(BigInteger ESTADOID, String ACTIVO);

    public Integer countByBancoIdAndEstadoIdAndActivo(BigInteger BANCOID, BigInteger ESTADOID, String ACTIVO);

}
