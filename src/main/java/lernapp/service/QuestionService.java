package lernapp.service;

import lernapp.model.*;

import javax.persistence.*;
import java.util.List;

public class QuestionService<T> {

    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("mariadb-localhost");


    /**
     * Generic Method for saving ONE ENTITY of any types of Entities
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
        //T result = em.merge(entity);

        //Das Objekt selbst wird gespeichert (create)
        em.persist(entity);

        em.getTransaction().commit();
        em.close();

        return entity;
    }

    /**
     * Generic Method for saving A LIST OF ENTITIES of any types of Entities
     * The Entity will be persisted to the database (not merged)
     *
     * @param  entities the Entity-List
     * @return entity
     *
     */
    public <T> List<T> save (List<T> entities) {

        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();

        //Kopie von object wird gespeichert (insert/update)
        //T result = em.merge(entity);

        //Das Objekt selbst wird gespeichert (create)
        for (T item : entities) {
            em.persist(item);
        }

        em.getTransaction().commit();
        em.close();

        return entities;
    }


    /**
     * Generic Method to query all entities
     *
     * @return List<T>
     *
     */
    public List<T> queryAll (Class<T> entityType) { // select t from tabellenname t

        EntityManager em = EMF.createEntityManager();
        String queryString = "select topicName from topic";
        Query query = em.createQuery(queryString);
        List<T> ts = query.getResultList();

        // TypedQuery<T> query = EMF.createEntityManager().createQuery(queryString, entityType);
        em.close();
        return ts;
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

