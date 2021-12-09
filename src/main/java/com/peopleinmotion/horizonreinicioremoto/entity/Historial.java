/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author avbravo
 */
@Entity
@Table(name = "HISTORIAL") 
@NamedQueries({
    @NamedQuery(name = "Historial.findAll", query = "SELECT h FROM Historial h"),
    @NamedQuery(name = "Historial.findByHistorialId", query = "SELECT h FROM Historial h WHERE h.HISTORIALID = :HISTORIALID"),
    @NamedQuery(name = "Historial.findByTabla", query = "SELECT h FROM Historial h WHERE h.TABLA = :TABLA"),
    @NamedQuery(name = "Historial.findByModulo", query = "SELECT h FROM Historial h WHERE h.MODULO = :MODULO"),
    @NamedQuery(name = "Historial.findByEvento", query = "SELECT h FROM Historial h WHERE h.EVENTO = :EVENTO"),
    @NamedQuery(name = "Historial.findByUsuarioId", query = "SELECT h FROM Historial h WHERE h.USUARIOID = :USUARIOID"),
    @NamedQuery(name = "Historial.findByFecha", query = "SELECT h FROM Historial h WHERE h.FECHA= :FECHA")})
public class Historial implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
   @Id
    @SequenceGenerator(name = "HISTORIAL_GEN", sequenceName = "HISTORIAL_SEQ",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HISTORIAL_GEN")
    @NotNull
    @Column(name = "HISTORIALID")
    private BigInteger HISTORIALID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TABLA")
    private String TABLA;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "MODULO")
    private String MODULO;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "EVENTO")
    private String EVENTO;
    @Lob
    @Column(name = "CONTENIDO")
    private String CONTENIDO;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USUARIOID")
    private BigInteger USUARIOID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date FECHA;

    public Historial() {
    }

    public Historial(BigInteger  HISTORIALID) {
        this.HISTORIALID = HISTORIALID;
    }

    public BigInteger getHISTORIALID() {
        return HISTORIALID;
    }

    public void setHISTORIALID(BigInteger HISTORIALID) {
        this.HISTORIALID = HISTORIALID;
    }

    public String getTABLA() {
        return TABLA;
    }

    public void setTABLA(String TABLA) {
        this.TABLA = TABLA;
    }

    public String getMODULO() {
        return MODULO;
    }

    public void setMODULO(String MODULO) {
        this.MODULO = MODULO;
    }

    public String getEVENTO() {
        return EVENTO;
    }

    public void setEVENTO(String EVENTO) {
        this.EVENTO = EVENTO;
    }

    public String getCONTENIDO() {
        return CONTENIDO;
    }

    public void setCONTENIDO(String CONTENIDO) {
        this.CONTENIDO = CONTENIDO;
    }

    public BigInteger getUSUARIOID() {
        return USUARIOID;
    }

    public void setUSUARIOID(BigInteger USUARIOID) {
        this.USUARIOID = USUARIOID;
    }

    public Date getFECHA() {
        return FECHA;
    }

    public void setFECHA(Date FECHA) {
        this.FECHA = FECHA;
    }

    
    
    
   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (HISTORIALID != null ? HISTORIALID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historial)) {
            return false;
        }
        Historial other = (Historial) object;
        if ((this.HISTORIALID == null && other.HISTORIALID != null) || (this.HISTORIALID != null && !this.HISTORIALID.equals(other.HISTORIALID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.avbravo.prueba.controller.newentity.Historial[ HISTORIALID=" + HISTORIALID + " ]";
    }
    
}
