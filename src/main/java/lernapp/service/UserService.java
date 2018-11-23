package lernapp.service;

import lernapp.model.*;

import javax.persistence.*;
import java.util.List;

public class UserService {

    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("mariadb-localhost");
    private Object Question;

    // Query an entity by id
    public User queryById(Long id) {
        return EMF.createEntityManager().find(User.class, id);
    }

    // Query all entities
    public List<User> queryAll() {
        String queryString = "SELECT t FROM " + User.class.getName() + " t";
        TypedQuery<User> query = EMF.createEntityManager().createQuery(queryString, User.class);
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
        User result = em.find(User.class, id);
        if (result != null) em.remove(result);
        em.getTransaction().commit();
        em.close();
    }

    public User queryByCredentials (String email, String password) {

        try {
            EntityManager em = EMF.createEntityManager();
            String queryString = "FROM user WHERE email = :mail AND password = :p";
            TypedQuery<User> q = em.createQuery(queryString, User.class);
            q.setParameter("mail", email);
            q.setParameter("p", password);
            User user = q.getSingleResult();
            em.close();
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

}

