package lernapp.service;

import lernapp.model.*;

import javax.persistence.*;
import java.util.List;

public class LearningStateService extends BasicService<LearningState> {

    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("mariadb-localhost");

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

    public UserQuestionLS save(UserQuestionLS entity) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        UserQuestionLS result = em.merge(entity);
        em.getTransaction().commit();
        em.close();
        return result;
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

    */
    public LearningState queryLearningState(User user, Question question) {
        EntityManager em = EMF.createEntityManager();

//        SELECT * FROM learningState st
//        JOIN userQuestionLs uqls
//        ON uqls.learningState_learningStateID = st.learningStateID
//        WHERE uqls.user_email = "marileen.stamer@stud.fh-luebeck.de"
//        AND uqls.`question_questionID` = 11 #AND uqls.user = :user"

        String queryString = "SELECT st FROM learningState st JOIN "+ UserQuestionLS.class.getName() + " uqls ON uqls.learningState = st WHERE uqls.question = :question AND uqls.user = :user";

        //String queryString = "SELECT q FROM question q JOIN topic t ON t.course = :course WHERE q.topic = t";
        TypedQuery<LearningState> query = em.createQuery(queryString, LearningState.class);
        query.setParameter("user", user ).setParameter("question", question);

        return query.getSingleResult();
    }

}

