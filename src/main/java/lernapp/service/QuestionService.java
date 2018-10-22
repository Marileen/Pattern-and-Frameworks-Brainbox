package lernapp.service;

import lernapp.model.Question;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class QuestionService<Question>{

    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("mariadb-localhost");

    private final Class<Question> entityType;

    public QuestionService(Class<Question> entityType) {
        this.entityType = entityType;
    }

    // Query an entity by id
    public Question queryById(Long id) {
        return EMF.createEntityManager().find(entityType, id);
    }

    // Query all entities
    public List<Question> queryAll() {
        String queryString = "SELECT t FROM " + entityType.getName() + " t";
        TypedQuery<Question> query = EMF.createEntityManager().createQuery(queryString, entityType);
        return query.getResultList();
    }

    // Create or update an entity
    public Question save(Question entity) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        Question result = em.merge(entity);
        em.getTransaction().commit();
        em.close();
        return result;
    }

    // Delete entity by id
    public void deleteById(Long id) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        Question result = em.find(entityType, id);
        if (result != null) em.remove(result);
        em.getTransaction().commit();
        em.close();
    }
}

