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
public class TokoJpaController implements Serializable {

    public TokoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Toko toko) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Barang idBarangOrphanCheck = toko.getIdBarang();
        if (idBarangOrphanCheck != null) {
            Toko oldTokoOfIdBarang = idBarangOrphanCheck.getToko();
            if (oldTokoOfIdBarang != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Barang " + idBarangOrphanCheck + " already has an item of type Toko whose idBarang column cannot be null. Please make another selection for the idBarang field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barang idBarang = toko.getIdBarang();
            if (idBarang != null) {
                idBarang = em.getReference(idBarang.getClass(), idBarang.getIdBarang());
                toko.setIdBarang(idBarang);
            }
            em.persist(toko);
            if (idBarang != null) {
                idBarang.setToko(toko);
                idBarang = em.merge(idBarang);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findToko(toko.getIdToko()) != null) {
                throw new PreexistingEntityException("Toko " + toko + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Toko toko) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Toko persistentToko = em.find(Toko.class, toko.getIdToko());
            Barang idBarangOld = persistentToko.getIdBarang();
            Barang idBarangNew = toko.getIdBarang();
            List<String> illegalOrphanMessages = null;
            if (idBarangNew != null && !idBarangNew.equals(idBarangOld)) {
                Toko oldTokoOfIdBarang = idBarangNew.getToko();
                if (oldTokoOfIdBarang != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Barang " + idBarangNew + " already has an item of type Toko whose idBarang column cannot be null. Please make another selection for the idBarang field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idBarangNew != null) {
                idBarangNew = em.getReference(idBarangNew.getClass(), idBarangNew.getIdBarang());
                toko.setIdBarang(idBarangNew);
            }
            toko = em.merge(toko);
            if (idBarangOld != null && !idBarangOld.equals(idBarangNew)) {
                idBarangOld.setToko(null);
                idBarangOld = em.merge(idBarangOld);
            }
            if (idBarangNew != null && !idBarangNew.equals(idBarangOld)) {
                idBarangNew.setToko(toko);
                idBarangNew = em.merge(idBarangNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = toko.getIdToko();
                if (findToko(id) == null) {
                    throw new NonexistentEntityException("The toko with id " + id + " no longer exists.");
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
            Toko toko;
            try {
                toko = em.getReference(Toko.class, id);
                toko.getIdToko();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The toko with id " + id + " no longer exists.", enfe);
            }
            Barang idBarang = toko.getIdBarang();
            if (idBarang != null) {
                idBarang.setToko(null);
                idBarang = em.merge(idBarang);
            }
            em.remove(toko);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Toko> findTokoEntities() {
        return findTokoEntities(true, -1, -1);
    }

    public List<Toko> findTokoEntities(int maxResults, int firstResult) {
        return findTokoEntities(false, maxResults, firstResult);
    }

    private List<Toko> findTokoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Toko.class));
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

    public Toko findToko(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Toko.class, id);
        } finally {
            em.close();
        }
    }

    public int getTokoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Toko> rt = cq.from(Toko.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
