package lernapp.service;

import lernapp.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserService <User> {

    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("mariadb-localhost");

    private final Class<User> entityType;


    public UserService(Class<User> entityType) {
        this.entityType = entityType;
    }


    // Query an entity by id
    public User queryById(Long id) {
        return EMF.createEntityManager().find(entityType, id);
    }

    // Query all entities
    public List<User> queryAll() {
        String queryString = "SELECT t FROM " + entityType.getName() + " t";
        TypedQuery<User> query = EMF.createEntityManager().createQuery(queryString, entityType);
        return query.getResultList();
    }

    // Create or update an entity
    public User save(User entity) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        User result = em.merge(entity);
        em.getTransaction().commit();
        em.close();
        return result;
    }

    // Delete entity by id
    public void deleteById(Long id) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        User result = em.find(entityType, id);
        if (result != null) em.remove(result);
        em.getTransaction().commit();
        em.close();
    }
}

