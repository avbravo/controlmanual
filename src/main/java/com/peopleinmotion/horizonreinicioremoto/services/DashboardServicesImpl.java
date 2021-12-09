/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.services;

import com.peopleinmotion.horizonreinicioremoto.entity.Banco;
import com.peopleinmotion.horizonreinicioremoto.entity.Estado;
import com.peopleinmotion.horizonreinicioremoto.entity.GrupoEstado;
import com.peopleinmotion.horizonreinicioremoto.jmoordb.JmoordbContext;
import com.peopleinmotion.horizonreinicioremoto.repository.AgendaRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.EstadoRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.GrupoEstadoRepository;
import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author avbravo
 */
@Stateless
public class DashboardServicesImpl implements DashboardServices {
    
    @Inject
    GrupoEstadoRepository grupoEstadoRepository;
    
    @Inject
    EstadoRepository estadoRepository;
    
    @Inject
    AgendaRepository agendaRepository;

    // <editor-fold defaultstate="collapsed" desc=" List<GrupoEstado> calcultateTotal(Banco banco) ">
    @Override
    public List<GrupoEstado> calcularTotalGrupoEstado(Banco banco) {
        List<GrupoEstado> grupoEstadoList = new ArrayList<>();
        try {
            
            grupoEstadoList = grupoEstadoRepository.findByActivo("SI");
            if (grupoEstadoList == null || grupoEstadoList.isEmpty()) {
                JsfUtil.warningMessage("No hay grupos de estados disponibles para realizar las operaciones");
            } else {
                Integer n = 0;
                for (GrupoEstado ge : grupoEstadoList) {
                    grupoEstadoList.get(n++).setTOTAL(JsfUtil.toBigInteger(0));
                }
                
                List<Estado> estadoList = estadoRepository.findByActivo("SI");
                if (estadoList == null || estadoList.isEmpty()) {
                    JsfUtil.warningMessage("No hay estados disponibles para realizar las operaciones");
                } else {
                    Integer count = 0;
                    for (Estado e : estadoList) {
                        // Cuenta los estados activos del banco
                        count = agendaRepository.countByBancoIdAndEstadoIdAndActivo(banco.getBANCOID(), e.getESTADOID(), "SI");
                        /**
                         * Actualizo los totales del grupo
                         */
                        Integer c = 0;
                        for (GrupoEstado ge : grupoEstadoList) {
                            if (ge.getGRUPOESTADOID().equals(e.getGRUPOESTADOID().getGRUPOESTADOID())) {
                                BigInteger total = grupoEstadoList.get(c).getTOTAL().add(JsfUtil.toBigInteger(count));
                                grupoEstadoList.get(c).setTOTAL(total);
                            }
                            c++;
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            JsfUtil.errorMessage("onCommandButttonCalcularTotales()" + e.getLocalizedMessage());
        }
        return grupoEstadoList;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="BigInteger totalSolicitado(List<GrupoEstado> grupoEstadoList)">
    @Override
    public BigInteger totalSolicitado(List<GrupoEstado> grupoEstadoList) {
        BigInteger total = new BigInteger("0");
        try {
            for (GrupoEstado ge : grupoEstadoList) {
              
                if (ge.getGRUPOESTADOID().equals(JsfUtil.contextToBigInteger("grupoEstadoSolicitadoId"))) {
                    total = ge.getTOTAL();
                    break;
                }

            }
            
        } catch (Exception e) {
            JsfUtil.errorMessage("totalEnProceso()" + e.getLocalizedMessage());
        }
        return total;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="BigInteger totalEnProceso(List<GrupoEstado> grupoEstadoList)  ">
    @Override
    public BigInteger totalEnProceso(List<GrupoEstado> grupoEstadoList) {
        BigInteger total = new BigInteger("0");
        try {
             for (GrupoEstado ge : grupoEstadoList) {
                if (ge.getGRUPOESTADOID().equals(JsfUtil.contextToBigInteger("grupoEstadoEnprocesoId"))) {
                    total = ge.getTOTAL();
                    break;
                }
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("totalEnProceso()" + e.getLocalizedMessage());
        }
        return total;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="BigInteger totalFinalizado(List<GrupoEstado> grupoEstadoList) ">

    @Override
    public BigInteger totalFinalizado(List<GrupoEstado> grupoEstadoList) {
        BigInteger total = new BigInteger("0");
        try {
             for (GrupoEstado ge : grupoEstadoList) {
                if (ge.getGRUPOESTADOID().equals(JsfUtil.contextToBigInteger("grupoEstadoFinalizadoId"))) {
                    total = ge.getTOTAL();
                    break;
                }
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("totalFinalizado()" + e.getLocalizedMessage());
        }
        return total;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="BigInteger totalNoSePuedeEjecutar(List<GrupoEstado> list)">

    @Override
    public BigInteger totalNoSePuedeEjecutar(List<GrupoEstado> grupoEstadoList) {
        BigInteger total = new BigInteger("0");
        try {
             for (GrupoEstado ge : grupoEstadoList) {
                if (ge.getGRUPOESTADOID().equals(JsfUtil.contextToBigInteger("grupoEstadoNoSepuedejecutarId"))) {
                    total = ge.getTOTAL();
                    break;
                }
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("totalNoSePuedeEjecutar()" + e.getLocalizedMessage());
        }
        return total;
    }
    // </editor-fold>
}
