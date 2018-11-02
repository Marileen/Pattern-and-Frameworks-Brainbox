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

    // Create or update an entity
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


    // Query all entities
    public List<Question> queryAll() {
        String queryString = "SELECT t FROM " + Question.class.getName() + " t";
        TypedQuery<Question> query = EMF.createEntityManager().createQuery(queryString, Question.class);
        return query.getResultList();
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

