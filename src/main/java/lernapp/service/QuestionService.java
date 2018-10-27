package lernapp.service;

import lernapp.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class QuestionService {

    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("mariadb-localhost");

    // Query an entity by id
    public Question queryById(Long id) {

        return EMF.createEntityManager().find(Question.class, id);
    }

    /*
    *
    * Test um Generics zu verstehen
    *
    *
    */

    public Question testMethode(Question qust) {

        // ...
        return qust;
    }

    public User testMethode(User u) {

        // ...
        return u;
    }

    public <T> T fancyMethode(T blubb) {

        // ...
        return blubb;
    }



    // Query all entities
    public List<Question> queryAll() {
        String queryString = "SELECT t FROM " + Question.class.getName() + " t";
        TypedQuery<Question> query = EMF.createEntityManager().createQuery(queryString, Question.class);
        return query.getResultList();
    }

    // Create or update an entity
    public Question save(Question entity) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();

        // kopie von object wird gespeichert - insert/update
        //Question result = em.merge(entity);

        // object selbst wird gespeichert - create?
        em.persist(entity);

        entity.answer = "blubb"; //könnte man machen, wird dann mit persistiert

        em.getTransaction().commit();
        em.close();
        return entity;
    }

    public Topic save (Topic entity) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();

        // kopie von object wird gespeichert - insert/update
        Topic result = em.merge(entity);

        // object selbst wird gespeichert - create?
        //em.persist(entity);

        //entity.topicName = "blubb"; //könnte man machen, wird dann mit persistiert

        em.getTransaction().commit();
        em.close();
        return entity;
    }

    public Course save(Course entity) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();

        // kopie von object wird gespeichert - insert/update
        Course result = em.merge(entity);

        // object selbst wird gespeichert - create?
        //em.persist(entity);

        //entity.topicName = "blubb"; //könnte man machen, wird dann mit persistiert

        em.getTransaction().commit();
        em.close();
        return entity;
    }

    public LearningState save(LearningState entity) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();

        // kopie von object wird gespeichert - insert/update
        LearningState result = em.merge(entity);

        // object selbst wird gespeichert - create?
        //em.persist(entity);

        //entity.topicName = "blubb"; //könnte man machen, wird dann mit persistiert

        em.getTransaction().commit();
        em.close();
        return entity;
    }

    public User_Question_LS save(User_Question_LS entity) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();

        // kopie von object wird gespeichert - insert/update
        User_Question_LS result = em.merge(entity);

        // object selbst wird gespeichert - create?
        //em.persist(entity);

        //entity.topicName = "blubb"; //könnte man machen, wird dann mit persistiert

        em.getTransaction().commit();
        em.close();
        return entity;
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

