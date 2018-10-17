package lernapp.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

// enables core functions for the App
public class GenericService<T> {

    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("mariadb-localhost");

    private final Class<T> entityType;

    public GenericService(Class<T> entityType) {
        this.entityType = entityType;
    }

    // Query an entity by id
    public T queryById(Long id) {
        return EMF.createEntityManager().find(entityType, id);
    }

    // Query all entities
    public List<T> queryAll() {
        String queryString = "SELECT t FROM " + entityType.getName() + " t";
        TypedQuery<T> query = EMF.createEntityManager().createQuery(queryString, entityType);
        return query.getResultList();
    }

    // Create or update an entity
    public T save(T entity) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        T result = em.merge(entity);
        em.getTransaction().commit();
        em.close();
        return result;
    }

    // Delete entity by id
    public void deleteById(Long id) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        T result = em.find(entityType, id);
        if (result != null) em.remove(result);
        em.getTransaction().commit();
        em.close();
    }
}

