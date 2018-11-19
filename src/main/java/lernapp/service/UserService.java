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

    /* zeigt alle Questions mit bestimmtem LS, z. B. "kann ich"
    -> Funktion braucht als Parameter: den eingeloggten Benutzer und den gewünschten LS -> return: alle Fragen

    public Question getMarkedQuestion(User userID, LearningState learningStateID){
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();

        Question resultSet = em.find(userID, learningStateID);

        em.getTransaction().commit();
        em.close();
        return resultSet;
    }
    */

   /* zeigt LS zu einer bestimmten Frage an -> Parameter: eingeloggter Benutzer + betreffende Frage -> return: LS
    public LearningState getLearningState(User userID, Question questionID) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();

        LearningState result;
        result = em.find(userID, questionID);

        em.getTransaction().commit();
        em.close();
        return result; // enthält den LearningState zu dieser bestimmten Frage
    }
    */


}

