package lernapp.service;

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

    // Singleton & Factory Pattern
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

        // merge: create copy from object for insert / update to the database
        // T result = em.merge(entity);

        // object itself will be saved (created) in the database incl. all changes of the object until commit
        em.persist(entity);

        em.getTransaction().commit();
        em.close();

        return entity;
    }

        /**
         * Generic Method for saving A LIST OF ENTITIES of any types of Entities
         * The Entities will be persisted to the database (not merged)
         *
         * @param  entities the Entity-List
         * @return entity
         *
         */
    public <T> List<T> save (List<T> entities) {

        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();

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

        EntityManager em = EMF.createEntityManager();
        String queryString = "FROM " + entityClass.getName();
        TypedQuery<T> query = em.createQuery(queryString, entityClass);

        return query.getResultList();
    }


    /**
     * Generic Method to query an entity by id
     *
     * @param id
     * @return entity
     *
     */
    public T queryById(Long id) {
        return EMF.createEntityManager().find(entityClass, id);
    }

    /**
     * Generic Method to delete an entity by id
     *
     * @param id
     *
     */
    // Delete entity by id
    public void deleteById(Long id) {

        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();

        T result = em.find(entityClass, id);
        if (result != null) em.remove(result);

        em.getTransaction().commit();
        em.close();
    }


}

