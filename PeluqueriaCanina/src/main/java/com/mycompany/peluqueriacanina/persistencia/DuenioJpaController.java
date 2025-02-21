package com.mycompany.peluqueriacanina.persistencia;

import com.mycompany.peluqueriacanina.logica.Duenio;
import com.mycompany.peluqueriacanina.persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DuenioJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public DuenioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PeluCaninaPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Duenio duenio) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(duenio);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void edit(Duenio duenio) throws NonexistentEntityException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            if (findDuenio(duenio.getId_duenio()) == null) {
                throw new NonexistentEntityException("El dueño con ID " + duenio.getId_duenio() + " no existe.");
            }
            em.merge(duenio);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Duenio duenio = em.find(Duenio.class, id);
            if (duenio == null) {
                throw new NonexistentEntityException("El dueño con ID " + id + " no existe.");
            }
            em.remove(duenio);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Duenio> findDuenioEntities() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT d FROM Duenio d", Duenio.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Duenio findDuenio(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Duenio.class, id);
        } finally {
            em.close();
        }
    }
}
