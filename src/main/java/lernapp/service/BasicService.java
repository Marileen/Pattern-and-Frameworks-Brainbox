package lernapp.service;

import lernapp.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/*
*
* Basic Service for Database Operations
*
* save single entity
* save list of entities
*
* query all entities
* query entity by id
*
* delete entiy by id
*
* */

public class BasicService<T> {

    //Hier benutzen wir das SINGLETON PATTERN - wirklich? ist das nicht Factory?
    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("mariadb-localhost");

    private Class<T> entityClass;

    public BasicService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

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
    public List<T> queryAll () {

        EntityManager em = EMF.createEntityManager(); //SELECT t FROM [tablename] as t
        String queryString = "FROM " + entityClass.getName();
        TypedQuery<T> query = em.createQuery(queryString, entityClass);

        return query.getResultList();
    }


    // Query an entity by id
    public T queryById(Long id) {
        return EMF.createEntityManager().find(entityClass, id);
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

