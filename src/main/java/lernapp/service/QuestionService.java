package lernapp.service;

import lernapp.model.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class QuestionService extends BasicService<Question> {

    public QuestionService( ) {
        super(Question.class);
    }

    CourseService courseService = new CourseService();
    TopicService topicService = new TopicService();

    /**
     * Query Questions by a given courseName
     *
     * @param coursename
     * @return List<Question>
     *
     */
    public List<Question> queryCourseQuestions(String coursename) {

        Course course = courseService.queryCourseByName(coursename);

        EntityManager em = EMF.createEntityManager();
        String queryString = "SELECT q FROM question q JOIN topic t ON t.course = :course WHERE q.topic = t";

        TypedQuery<Question> query = em.createQuery(queryString, Question.class);
        query.setParameter("course", course );

        return query.getResultList();
    }

    /**
     * Query Questions by a given coursename and topicname
     *
     * @param coursename, topicname
     * @return List<Question>
     *
     */
    public List<Question> queryTopicQuestions(String coursename, String topicname) {

        Course course = courseService.queryCourseByName(coursename);
        Topic topic = topicService.queryTopicByName(topicname);

        EntityManager em = EMF.createEntityManager();
        String queryString = "SELECT q FROM question q JOIN topic t ON t.course = :course WHERE q.topic = t AND q.topic = :topic";

        TypedQuery<Question> query = em.createQuery(queryString, Question.class);
        query.setParameter("course", course ).setParameter("topic", topic);

        return query.getResultList();

    }


    /**
     * Query alle Questions that are marked with the same LearningState for a certain user
     *
     * @param state, user
     * @return List<Question>
     *
     */
    public List<Question> queryMarkedQuestions(LearningState state, User user) {

        EntityManager em = EMF.createEntityManager();
        String queryString = "SELECT q FROM question q JOIN userQuestionLs uqls ON uqls.question = q WHERE uqls.user = :user AND uqls = :state";

        TypedQuery<Question> query = em.createQuery(queryString, Question.class);
        query.setParameter("user", user ).setParameter("state", state);

        return query.getResultList();

    }


}

