/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "BANCO")
@NamedQueries({
    @NamedQuery(name = "Banco.findAll", query = "SELECT b FROM Banco b"),
    @NamedQuery(name = "Banco.findByBancoId", query = "SELECT b FROM Banco b WHERE b.BANCOID = :BANCOID"),
    @NamedQuery(name = "Banco.findByBanco", query = "SELECT b FROM Banco b WHERE b.BANCO = :BANCO"),
    @NamedQuery(name = "Banco.findByActivo", query = "SELECT b FROM Banco b WHERE b.ACTIVO= :ACTIVO"),
    @NamedQuery(name = "Banco.findByDescripcion", query = "SELECT b FROM Banco b WHERE b.DESCRIPCION = :DESCRIPCION"),
    @NamedQuery(name = "Banco.findByOrden", query = "SELECT b FROM Banco b WHERE b.ORDEN = :ORDEN"),
    @NamedQuery(name = "Banco.findByEsControl", query = "SELECT b FROM Banco b WHERE b.ESCONTROL = :ESCONTROL")
})
public class Banco implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "BANCO_GEN", sequenceName = "BANCO_SEQ",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BANCO_GEN")
    @NotNull
    @Column(name = "BANCOID")
    private BigInteger BANCOID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "BANCO")
    private String BANCO;
    @Basic(optional = false)
    @NotNull     
    @Column(name = "SIGLAS")
    private String SIGLAS;
    @Basic(optional = false)
    @NotNull     
    @Column(name = "ACTIVO")
    private String ACTIVO;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DESCRIPCION")
    private String DESCRIPCION;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDEN")
    private BigInteger ORDEN;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "ESCONTROL")
    private String ESCONTROL;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "BANCOID")
    private Collection<Usuario> usuarioCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "BANCOID")
    private Collection<Cajero> cajeroCollection;

    public Banco() {
    }

    public String getSIGLAS() {
        return SIGLAS;
    }

    public void setSIGLAS(String SIGLAS) {
        this.SIGLAS = SIGLAS;
    }

    
    
    public Banco(BigInteger BANCOID) {
        this.BANCOID = BANCOID;
    }

    public BigInteger getBANCOID() {
        return BANCOID;
    }

    public void setBANCOID(BigInteger BANCOID) {
        this.BANCOID = BANCOID;
    }

    public String getBANCO() {
        return BANCO;
    }

    public void setBANCO(String BANCO) {
        this.BANCO = BANCO;
    }

    public String getACTIVO() {
        return ACTIVO;
    }

    public void setACTIVO(String ACTIVO) {
        this.ACTIVO = ACTIVO;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public BigInteger getORDEN() {
        return ORDEN;
    }

    public void setORDEN(BigInteger ORDEN) {
        this.ORDEN = ORDEN;
    }

    public String getESCONTROL() {
        return ESCONTROL;
    }

    public void setESCONTROL(String ESCONTROL) {
        this.ESCONTROL = ESCONTROL;
    }

    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    public Collection<Cajero> getCajeroCollection() {
        return cajeroCollection;
    }

    public void setCajeroCollection(Collection<Cajero> cajeroCollection) {
        this.cajeroCollection = cajeroCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (BANCOID != null ? BANCOID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Banco)) {
            return false;
        }
        Banco other = (Banco) object;
        if ((this.BANCOID == null && other.BANCOID != null) || (this.BANCOID != null && !this.BANCOID.equals(other.BANCOID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.avbravo.prueba.controller.newentity.Banco[ BANCOID=" + BANCOID + " ]";
    }

}
