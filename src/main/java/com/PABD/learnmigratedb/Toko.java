/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PABD.learnmigratedb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author A S U S
 */
@Entity
@Table(name = "toko")
@NamedQueries({
    @NamedQuery(name = "Toko.findAll", query = "SELECT t FROM Toko t"),
    @NamedQuery(name = "Toko.findByIdToko", query = "SELECT t FROM Toko t WHERE t.idToko = :idToko"),
    @NamedQuery(name = "Toko.findByNamaToko", query = "SELECT t FROM Toko t WHERE t.namaToko = :namaToko"),
    @NamedQuery(name = "Toko.findByNomorTelp", query = "SELECT t FROM Toko t WHERE t.nomorTelp = :nomorTelp"),
    @NamedQuery(name = "Toko.findByAlamatToko", query = "SELECT t FROM Toko t WHERE t.alamatToko = :alamatToko"),
    @NamedQuery(name = "Toko.findByEmailToko", query = "SELECT t FROM Toko t WHERE t.emailToko = :emailToko")})
public class Toko implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_toko")
    private String idToko;
    @Basic(optional = false)
    @Column(name = "nama_toko")
    private String namaToko;
    @Basic(optional = false)
    @Column(name = "nomor_telp")
    private int nomorTelp;
    @Basic(optional = false)
    @Column(name = "alamat_toko")
    private String alamatToko;
    @Basic(optional = false)
    @Column(name = "email_toko")
    private String emailToko;
    @JoinColumn(name = "id_barang", referencedColumnName = "id_barang")
    @OneToOne(optional = false)
    private Barang idBarang;

    public Toko() {
    }

    public Toko(String idToko) {
        this.idToko = idToko;
    }

    public Toko(String idToko, String namaToko, int nomorTelp, String alamatToko, String emailToko) {
        this.idToko = idToko;
        this.namaToko = namaToko;
        this.nomorTelp = nomorTelp;
        this.alamatToko = alamatToko;
        this.emailToko = emailToko;
    }

    public String getIdToko() {
        return idToko;
    }

    public void setIdToko(String idToko) {
        this.idToko = idToko;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public int getNomorTelp() {
        return nomorTelp;
    }

    public void setNomorTelp(int nomorTelp) {
        this.nomorTelp = nomorTelp;
    }

    public String getAlamatToko() {
        return alamatToko;
    }

    public void setAlamatToko(String alamatToko) {
        this.alamatToko = alamatToko;
    }

    public String getEmailToko() {
        return emailToko;
    }

    public void setEmailToko(String emailToko) {
        this.emailToko = emailToko;
    }

    public Barang getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(Barang idBarang) {
        this.idBarang = idBarang;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idToko != null ? idToko.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Toko)) {
            return false;
        }
        Toko other = (Toko) object;
        if ((this.idToko == null && other.idToko != null) || (this.idToko != null && !this.idToko.equals(other.idToko))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.PABD.learnmigratedb.Toko[ idToko=" + idToko + " ]";
    }
    
}
