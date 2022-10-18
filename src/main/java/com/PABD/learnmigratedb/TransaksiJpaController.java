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
public class TransaksiJpaController implements Serializable {

    public TransaksiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transaksi transaksi) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Customer idPembeliOrphanCheck = transaksi.getIdPembeli();
        if (idPembeliOrphanCheck != null) {
            Transaksi oldTransaksiOfIdPembeli = idPembeliOrphanCheck.getTransaksi();
            if (oldTransaksiOfIdPembeli != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Customer " + idPembeliOrphanCheck + " already has an item of type Transaksi whose idPembeli column cannot be null. Please make another selection for the idPembeli field.");
            }
        }
        Barang idBarangOrphanCheck = transaksi.getIdBarang();
        if (idBarangOrphanCheck != null) {
            Transaksi oldTransaksiOfIdBarang = idBarangOrphanCheck.getTransaksi();
            if (oldTransaksiOfIdBarang != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Barang " + idBarangOrphanCheck + " already has an item of type Transaksi whose idBarang column cannot be null. Please make another selection for the idBarang field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer idPembeli = transaksi.getIdPembeli();
            if (idPembeli != null) {
                idPembeli = em.getReference(idPembeli.getClass(), idPembeli.getIdPembeli());
                transaksi.setIdPembeli(idPembeli);
            }
            Barang idBarang = transaksi.getIdBarang();
            if (idBarang != null) {
                idBarang = em.getReference(idBarang.getClass(), idBarang.getIdBarang());
                transaksi.setIdBarang(idBarang);
            }
            em.persist(transaksi);
            if (idPembeli != null) {
                idPembeli.setTransaksi(transaksi);
                idPembeli = em.merge(idPembeli);
            }
            if (idBarang != null) {
                idBarang.setTransaksi(transaksi);
                idBarang = em.merge(idBarang);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTransaksi(transaksi.getIdTransaksi()) != null) {
                throw new PreexistingEntityException("Transaksi " + transaksi + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transaksi transaksi) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaksi persistentTransaksi = em.find(Transaksi.class, transaksi.getIdTransaksi());
            Customer idPembeliOld = persistentTransaksi.getIdPembeli();
            Customer idPembeliNew = transaksi.getIdPembeli();
            Barang idBarangOld = persistentTransaksi.getIdBarang();
            Barang idBarangNew = transaksi.getIdBarang();
            List<String> illegalOrphanMessages = null;
            if (idPembeliNew != null && !idPembeliNew.equals(idPembeliOld)) {
                Transaksi oldTransaksiOfIdPembeli = idPembeliNew.getTransaksi();
                if (oldTransaksiOfIdPembeli != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Customer " + idPembeliNew + " already has an item of type Transaksi whose idPembeli column cannot be null. Please make another selection for the idPembeli field.");
                }
            }
            if (idBarangNew != null && !idBarangNew.equals(idBarangOld)) {
                Transaksi oldTransaksiOfIdBarang = idBarangNew.getTransaksi();
                if (oldTransaksiOfIdBarang != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Barang " + idBarangNew + " already has an item of type Transaksi whose idBarang column cannot be null. Please make another selection for the idBarang field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idPembeliNew != null) {
                idPembeliNew = em.getReference(idPembeliNew.getClass(), idPembeliNew.getIdPembeli());
                transaksi.setIdPembeli(idPembeliNew);
            }
            if (idBarangNew != null) {
                idBarangNew = em.getReference(idBarangNew.getClass(), idBarangNew.getIdBarang());
                transaksi.setIdBarang(idBarangNew);
            }
            transaksi = em.merge(transaksi);
            if (idPembeliOld != null && !idPembeliOld.equals(idPembeliNew)) {
                idPembeliOld.setTransaksi(null);
                idPembeliOld = em.merge(idPembeliOld);
            }
            if (idPembeliNew != null && !idPembeliNew.equals(idPembeliOld)) {
                idPembeliNew.setTransaksi(transaksi);
                idPembeliNew = em.merge(idPembeliNew);
            }
            if (idBarangOld != null && !idBarangOld.equals(idBarangNew)) {
                idBarangOld.setTransaksi(null);
                idBarangOld = em.merge(idBarangOld);
            }
            if (idBarangNew != null && !idBarangNew.equals(idBarangOld)) {
                idBarangNew.setTransaksi(transaksi);
                idBarangNew = em.merge(idBarangNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = transaksi.getIdTransaksi();
                if (findTransaksi(id) == null) {
                    throw new NonexistentEntityException("The transaksi with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaksi transaksi;
            try {
                transaksi = em.getReference(Transaksi.class, id);
                transaksi.getIdTransaksi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaksi with id " + id + " no longer exists.", enfe);
            }
            Customer idPembeli = transaksi.getIdPembeli();
            if (idPembeli != null) {
                idPembeli.setTransaksi(null);
                idPembeli = em.merge(idPembeli);
            }
            Barang idBarang = transaksi.getIdBarang();
            if (idBarang != null) {
                idBarang.setTransaksi(null);
                idBarang = em.merge(idBarang);
            }
            em.remove(transaksi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transaksi> findTransaksiEntities() {
        return findTransaksiEntities(true, -1, -1);
    }

    public List<Transaksi> findTransaksiEntities(int maxResults, int firstResult) {
        return findTransaksiEntities(false, maxResults, firstResult);
    }

    private List<Transaksi> findTransaksiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transaksi.class));
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

    public Transaksi findTransaksi(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transaksi.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransaksiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transaksi> rt = cq.from(Transaksi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
