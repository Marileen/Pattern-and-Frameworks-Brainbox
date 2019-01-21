package lernapp.service;

import lernapp.model.*;

import javax.persistence.*;
import java.util.List;

public class LearningStateService extends BasicService<LearningState> {

    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("mariadb-localhost");

    public LearningStateService() {
        super(LearningState.class);
    }

    /**
     * Query all LearningStates
     * @return List<LearningState>
     *
     */
    public List<LearningState> queryAll() {
        String queryString = "SELECT t FROM " + LearningState.class.getName() + " t";
        EntityManager em = EMF.createEntityManager();
        TypedQuery<LearningState> query = em.createQuery(queryString, LearningState.class);

        List<LearningState> lsList = query.getResultList();
        em.close();
        return lsList;
    }

    /**
     * save a LearningState for a certain user and a certain question
     * @param UserQuestionLS
     * @return UserQuestionLS
     *
     */
    public UserQuestionLS save(UserQuestionLS entity) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        UserQuestionLS result = em.merge(entity);
        em.getTransaction().commit();
        em.close();
        return result;
    }

    /**
     * queries LearningState for a certain question
     * @param User, Question
     * @return LearningState
     *
     */
    public LearningState queryLearningState(User user, Question question) {
        EntityManager em = EMF.createEntityManager();

        String queryString = "SELECT st FROM learningState st JOIN "+ UserQuestionLS.class.getName() + " uqls ON uqls.learningState = st WHERE uqls.question = :question AND uqls.user = :user";

        TypedQuery<LearningState> query = em.createQuery(queryString, LearningState.class);
        query.setParameter("user", user ).setParameter("question", question);

        return query.getSingleResult();
    }

    /**
     * queries a list of questions for a certain user ID and a certain LearningState ID
     * @param userId, lsId
     * @return List<Question>
     *
     */
    public List<Question> queryQuestionsForLearningStateAndUser(Long userId, Long lsId) {

        UserService userService = new UserService();
        LearningState ls = this.queryById(lsId);
        User user = userService.queryById(userId);

        EntityManager em = EMF.createEntityManager();

        String queryString = "SELECT q FROM question q JOIN userQuestionLs uqls ON uqls.learningState = :ls WHERE uqls.user = :user";

        TypedQuery<Question> query = em.createQuery(queryString, Question.class);

        query.setParameter("ls", ls ).setParameter("user", user);

        List<Question> questions = query.getResultList();

        em.close();

        return questions;

    }

}

