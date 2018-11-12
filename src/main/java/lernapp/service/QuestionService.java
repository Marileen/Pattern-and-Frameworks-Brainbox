package lernapp.service;

import lernapp.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class QuestionService<T> {

    //Hier benutzen wir das SINGLETON PATTTERN
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
    public List<T> queryAll (Class<T> entityType) {

        EntityManager em = EMF.createEntityManager(); //SELECT t FROM [tablename] as t
        String queryString = "FROM " + entityType.getName();
        TypedQuery<T> query = em.createQuery(queryString, entityType);

        return query.getResultList();
    }

    /**
     * Query Topics that belongs to a specific Course
     *
     * @return List<Topic>
     *
     */
    public List<Topic> queryCourseTopics(String courseName) {

        Course course = this.queryCourseByName(courseName);

        EntityManager em = EMF.createEntityManager();
        String queryString = "FROM "+ Topic.class.getName() + " WHERE course = :courseId";

        TypedQuery<Topic> query = em.createQuery(queryString, Topic.class);
        query.setParameter("courseId", course );

        return query.getResultList();
    }


        /**
         * Query specific Topic
         *
         * @return List<Topic>
         *
         */
    public List<Topic> queryCourseTopic(String courseName, String topicName) {

        Course course = this.queryCourseByName(courseName);

        EntityManager em = EMF.createEntityManager();
        String queryString = "FROM "+ Topic.class.getName() + " WHERE course = :courseId AND topicName = :topicName";
        //String queryString = "FROM " + Topic.class.getName() + " WHERE topicName = :tName";

        TypedQuery<Topic> query = em.createQuery(queryString, Topic.class);
        query.setParameter("courseId", course ).setParameter("topicName", topicName );

//        String queryString = "SELECT o FROM Order o WHERE o.customer.id = :id";
//        TypedQuery<Order> q = super.EMF.createEntityManager().createQuery(queryString, Order.class);
//        q.setParameter("id", customerId);

        return query.getResultList();

    }

    /**
     * Query a Course by name
     *
     * @return Course
     *
     */
    public Course queryCourseByName(String courseName) {

        EntityManager em = EMF.createEntityManager();

        String queryString = "FROM " +  Course.class.getName() + " WHERE courseName =:cName";

        // an dieser Stelle kann es eine Exception geben todo: abfangen
        TypedQuery<Course> query = em.createQuery(queryString, Course.class);
        query.setParameter( "cName", courseName );

        return query.getSingleResult();
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

