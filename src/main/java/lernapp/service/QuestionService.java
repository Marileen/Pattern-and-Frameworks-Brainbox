package lernapp.service;

import lernapp.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class QuestionService<T> {

    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("mariadb-localhost");


    /**
     * Generic Method for saving all types of Entities
     * The Entity will be persisted to the database (not merged)
     *
     * @param  entity  the Entity to be saved
     * @return entity
     *
     */
    public <T> T save (T entity) {

        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();

        //Kopie von object wird gespeichert (insert/update)
        //Question result = em.merge(entity);

        //Das Objekt selbst wird gespeichert (create)
        em.persist(entity);

        em.getTransaction().commit();
        em.close();

        return entity;
    }


    /**
     * Generic Method to query all entities
     *
     * @return List<T>
     *
     */
    public List<T> queryAll (Class<T> entityType) {
        String queryString = "SELECT t FROM " + entityType.getName() + " t";
        TypedQuery<T> query = EMF.createEntityManager().createQuery(queryString, entityType);
        return query.getResultList();
    }

    // Query an entity by id
    public Question queryById(Long id) {
        return EMF.createEntityManager().find(Question.class, id);
    }

    // Delete entity by id
    public void deleteById(Long id) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        Question result = em.find(Question.class, id);
        if (result != null) em.remove(result);
        em.getTransaction().commit();
        em.close();
    }


}

