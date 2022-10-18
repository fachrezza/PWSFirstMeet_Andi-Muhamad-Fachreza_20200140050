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
@Table(name = "transaksi")
@NamedQueries({
    @NamedQuery(name = "Transaksi.findAll", query = "SELECT t FROM Transaksi t"),
    @NamedQuery(name = "Transaksi.findByIdTransaksi", query = "SELECT t FROM Transaksi t WHERE t.idTransaksi = :idTransaksi"),
    @NamedQuery(name = "Transaksi.findByTanggalTransaksi", query = "SELECT t FROM Transaksi t WHERE t.tanggalTransaksi = :tanggalTransaksi"),
    @NamedQuery(name = "Transaksi.findByTotalTransaksi", query = "SELECT t FROM Transaksi t WHERE t.totalTransaksi = :totalTransaksi"),
    @NamedQuery(name = "Transaksi.findByJumlahTransaksi", query = "SELECT t FROM Transaksi t WHERE t.jumlahTransaksi = :jumlahTransaksi")})
public class Transaksi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_transaksi")
    private String idTransaksi;
    @Basic(optional = false)
    @Column(name = "tanggal_transaksi")
    private String tanggalTransaksi;
    @Basic(optional = false)
    @Column(name = "total_transaksi")
    private int totalTransaksi;
    @Basic(optional = false)
    @Column(name = "jumlah_transaksi")
    private int jumlahTransaksi;
    @JoinColumn(name = "id_pembeli", referencedColumnName = "id_pembeli")
    @OneToOne(optional = false)
    private Customer idPembeli;
    @JoinColumn(name = "id_barang", referencedColumnName = "id_barang")
    @OneToOne(optional = false)
    private Barang idBarang;

    public Transaksi() {
    }

    public Transaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public Transaksi(String idTransaksi, String tanggalTransaksi, int totalTransaksi, int jumlahTransaksi) {
        this.idTransaksi = idTransaksi;
        this.tanggalTransaksi = tanggalTransaksi;
        this.totalTransaksi = totalTransaksi;
        this.jumlahTransaksi = jumlahTransaksi;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(String tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public int getTotalTransaksi() {
        return totalTransaksi;
    }

    public void setTotalTransaksi(int totalTransaksi) {
        this.totalTransaksi = totalTransaksi;
    }

    public int getJumlahTransaksi() {
        return jumlahTransaksi;
    }

    public void setJumlahTransaksi(int jumlahTransaksi) {
        this.jumlahTransaksi = jumlahTransaksi;
    }

    public Customer getIdPembeli() {
        return idPembeli;
    }

    public void setIdPembeli(Customer idPembeli) {
        this.idPembeli = idPembeli;
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
        hash += (idTransaksi != null ? idTransaksi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaksi)) {
            return false;
        }
        Transaksi other = (Transaksi) object;
        if ((this.idTransaksi == null && other.idTransaksi != null) || (this.idTransaksi != null && !this.idTransaksi.equals(other.idTransaksi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.PABD.learnmigratedb.Transaksi[ idTransaksi=" + idTransaksi + " ]";
    }
    
}
