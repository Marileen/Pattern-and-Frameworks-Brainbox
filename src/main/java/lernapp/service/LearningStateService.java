package lernapp.service;

import lernapp.model.LearningState;

import javax.persistence.*;
import java.util.List;

public class LearningStateService {

    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("mariadb-localhost");
    private Object LearningState;

    // Query an entity by id
    public LearningState queryById(Long id) {
        return EMF.createEntityManager().find(LearningState.class, id);
    }

    // Query all entities
    public List<LearningState> queryAll() {
        String queryString = "SELECT t FROM " + LearningState.class.getName() + " t";
        TypedQuery<LearningState> query = EMF.createEntityManager().createQuery(queryString, LearningState.class);
        return query.getResultList();
    }

    // Create or update an entity
    public LearningState save(LearningState entity) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        LearningState result = em.merge(entity);
        em.getTransaction().commit();
        em.close();
        return result;
    }

    // Delete entity by id
    public void deleteById(Long id) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        LearningState result = em.find(LearningState.class, id);
        if (result != null) em.remove(result);
        em.getTransaction().commit();
        em.close();
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

