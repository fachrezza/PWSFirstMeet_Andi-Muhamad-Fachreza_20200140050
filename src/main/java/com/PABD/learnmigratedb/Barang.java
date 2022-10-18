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
@Table(name = "barang")
@NamedQueries({
    @NamedQuery(name = "Barang.findAll", query = "SELECT b FROM Barang b"),
    @NamedQuery(name = "Barang.findByNamabarang", query = "SELECT b FROM Barang b WHERE b.namabarang = :namabarang"),
    @NamedQuery(name = "Barang.findByIdBarang", query = "SELECT b FROM Barang b WHERE b.idBarang = :idBarang"),
    @NamedQuery(name = "Barang.findByStokBarang", query = "SELECT b FROM Barang b WHERE b.stokBarang = :stokBarang"),
    @NamedQuery(name = "Barang.findByHargaSatuan", query = "SELECT b FROM Barang b WHERE b.hargaSatuan = :hargaSatuan")})
public class Barang implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "Nama_barang")
    private String namabarang;
    @Id
    @Basic(optional = false)
    @Column(name = "id_barang")
    private String idBarang;
    @Basic(optional = false)
    @Column(name = "stok_barang")
    private int stokBarang;
    @Basic(optional = false)
    @Column(name = "harga_satuan")
    private int hargaSatuan;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idBarang")
    private Transaksi transaksi;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idBarang")
    private Toko toko;

    public Barang() {
    }

    public Barang(String idBarang) {
        this.idBarang = idBarang;
    }

    public Barang(String idBarang, String namabarang, int stokBarang, int hargaSatuan) {
        this.idBarang = idBarang;
        this.namabarang = namabarang;
        this.stokBarang = stokBarang;
        this.hargaSatuan = hargaSatuan;
    }

    public String getNamabarang() {
        return namabarang;
    }

    public void setNamabarang(String namabarang) {
        this.namabarang = namabarang;
    }

    public String getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(String idBarang) {
        this.idBarang = idBarang;
    }

    public int getStokBarang() {
        return stokBarang;
    }

    public void setStokBarang(int stokBarang) {
        this.stokBarang = stokBarang;
    }

    public int getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(int hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    public Toko getToko() {
        return toko;
    }

    public void setToko(Toko toko) {
        this.toko = toko;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBarang != null ? idBarang.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Barang)) {
            return false;
        }
        Barang other = (Barang) object;
        if ((this.idBarang == null && other.idBarang != null) || (this.idBarang != null && !this.idBarang.equals(other.idBarang))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.PABD.learnmigratedb.Barang[ idBarang=" + idBarang + " ]";
    }
    
}
