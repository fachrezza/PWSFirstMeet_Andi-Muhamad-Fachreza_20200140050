/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PABD.learnmigratedb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author A S U S
 */
@Entity
@Table(name = "customer")
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByIdPembeli", query = "SELECT c FROM Customer c WHERE c.idPembeli = :idPembeli"),
    @NamedQuery(name = "Customer.findByNamaPembeli", query = "SELECT c FROM Customer c WHERE c.namaPembeli = :namaPembeli"),
    @NamedQuery(name = "Customer.findByNoTelfon", query = "SELECT c FROM Customer c WHERE c.noTelfon = :noTelfon"),
    @NamedQuery(name = "Customer.findByAlamatPembeli", query = "SELECT c FROM Customer c WHERE c.alamatPembeli = :alamatPembeli")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_pembeli")
    private String idPembeli;
    @Basic(optional = false)
    @Column(name = "nama_pembeli")
    private String namaPembeli;
    @Basic(optional = false)
    @Column(name = "no_telfon")
    private String noTelfon;
    @Basic(optional = false)
    @Column(name = "alamat_pembeli")
    private String alamatPembeli;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idPembeli")
    private Transaksi transaksi;

    public Customer() {
    }

    public Customer(String idPembeli) {
        this.idPembeli = idPembeli;
    }

    public Customer(String idPembeli, String namaPembeli, String noTelfon, String alamatPembeli) {
        this.idPembeli = idPembeli;
        this.namaPembeli = namaPembeli;
        this.noTelfon = noTelfon;
        this.alamatPembeli = alamatPembeli;
    }

    public String getIdPembeli() {
        return idPembeli;
    }

    public void setIdPembeli(String idPembeli) {
        this.idPembeli = idPembeli;
    }

    public String getNamaPembeli() {
        return namaPembeli;
    }

    public void setNamaPembeli(String namaPembeli) {
        this.namaPembeli = namaPembeli;
    }

    public String getNoTelfon() {
        return noTelfon;
    }

    public void setNoTelfon(String noTelfon) {
        this.noTelfon = noTelfon;
    }

    public String getAlamatPembeli() {
        return alamatPembeli;
    }

    public void setAlamatPembeli(String alamatPembeli) {
        this.alamatPembeli = alamatPembeli;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPembeli != null ? idPembeli.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.idPembeli == null && other.idPembeli != null) || (this.idPembeli != null && !this.idPembeli.equals(other.idPembeli))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.PABD.learnmigratedb.Customer[ idPembeli=" + idPembeli + " ]";
    }
    
}
