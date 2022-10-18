/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PABD.learnmigratedb;

import com.PABD.learnmigratedb.exceptions.IllegalOrphanException;
import com.PABD.learnmigratedb.exceptions.NonexistentEntityException;
import com.PABD.learnmigratedb.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author A S U S
 */
public class BarangJpaController implements Serializable {

    public BarangJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Barang barang) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaksi transaksi = barang.getTransaksi();
            if (transaksi != null) {
                transaksi = em.getReference(transaksi.getClass(), transaksi.getIdTransaksi());
                barang.setTransaksi(transaksi);
            }
            Toko toko = barang.getToko();
            if (toko != null) {
                toko = em.getReference(toko.getClass(), toko.getIdToko());
                barang.setToko(toko);
            }
            em.persist(barang);
            if (transaksi != null) {
                Barang oldIdBarangOfTransaksi = transaksi.getIdBarang();
                if (oldIdBarangOfTransaksi != null) {
                    oldIdBarangOfTransaksi.setTransaksi(null);
                    oldIdBarangOfTransaksi = em.merge(oldIdBarangOfTransaksi);
                }
                transaksi.setIdBarang(barang);
                transaksi = em.merge(transaksi);
            }
            if (toko != null) {
                Barang oldIdBarangOfToko = toko.getIdBarang();
                if (oldIdBarangOfToko != null) {
                    oldIdBarangOfToko.setToko(null);
                    oldIdBarangOfToko = em.merge(oldIdBarangOfToko);
                }
                toko.setIdBarang(barang);
                toko = em.merge(toko);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBarang(barang.getIdBarang()) != null) {
                throw new PreexistingEntityException("Barang " + barang + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Barang barang) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barang persistentBarang = em.find(Barang.class, barang.getIdBarang());
            Transaksi transaksiOld = persistentBarang.getTransaksi();
            Transaksi transaksiNew = barang.getTransaksi();
            Toko tokoOld = persistentBarang.getToko();
            Toko tokoNew = barang.getToko();
            List<String> illegalOrphanMessages = null;
            if (transaksiOld != null && !transaksiOld.equals(transaksiNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Transaksi " + transaksiOld + " since its idBarang field is not nullable.");
            }
            if (tokoOld != null && !tokoOld.equals(tokoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Toko " + tokoOld + " since its idBarang field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (transaksiNew != null) {
                transaksiNew = em.getReference(transaksiNew.getClass(), transaksiNew.getIdTransaksi());
                barang.setTransaksi(transaksiNew);
            }
            if (tokoNew != null) {
                tokoNew = em.getReference(tokoNew.getClass(), tokoNew.getIdToko());
                barang.setToko(tokoNew);
            }
            barang = em.merge(barang);
            if (transaksiNew != null && !transaksiNew.equals(transaksiOld)) {
                Barang oldIdBarangOfTransaksi = transaksiNew.getIdBarang();
                if (oldIdBarangOfTransaksi != null) {
                    oldIdBarangOfTransaksi.setTransaksi(null);
                    oldIdBarangOfTransaksi = em.merge(oldIdBarangOfTransaksi);
                }
                transaksiNew.setIdBarang(barang);
                transaksiNew = em.merge(transaksiNew);
            }
            if (tokoNew != null && !tokoNew.equals(tokoOld)) {
                Barang oldIdBarangOfToko = tokoNew.getIdBarang();
                if (oldIdBarangOfToko != null) {
                    oldIdBarangOfToko.setToko(null);
                    oldIdBarangOfToko = em.merge(oldIdBarangOfToko);
                }
                tokoNew.setIdBarang(barang);
                tokoNew = em.merge(tokoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = barang.getIdBarang();
                if (findBarang(id) == null) {
                    throw new NonexistentEntityException("The barang with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barang barang;
            try {
                barang = em.getReference(Barang.class, id);
                barang.getIdBarang();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The barang with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Transaksi transaksiOrphanCheck = barang.getTransaksi();
            if (transaksiOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Barang (" + barang + ") cannot be destroyed since the Transaksi " + transaksiOrphanCheck + " in its transaksi field has a non-nullable idBarang field.");
            }
            Toko tokoOrphanCheck = barang.getToko();
            if (tokoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Barang (" + barang + ") cannot be destroyed since the Toko " + tokoOrphanCheck + " in its toko field has a non-nullable idBarang field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(barang);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Barang> findBarangEntities() {
        return findBarangEntities(true, -1, -1);
    }

    public List<Barang> findBarangEntities(int maxResults, int firstResult) {
        return findBarangEntities(false, maxResults, firstResult);
    }

    private List<Barang> findBarangEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Barang.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Barang findBarang(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Barang.class, id);
        } finally {
            em.close();
        }
    }

    public int getBarangCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Barang> rt = cq.from(Barang.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
