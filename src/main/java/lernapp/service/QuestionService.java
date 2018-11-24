package lernapp.service;

import lernapp.model.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class QuestionService extends BasicService<Question> {

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
     * Query Questions by a given Course
     *
     * @return List<Question>
     *
     */
    public List<Question> queryCourseQuestions(String coursename) {

        Course course = queryCourseByName(coursename);

        EntityManager em = EMF.createEntityManager();
        String queryString = "SELECT q FROM question q JOIN topic t ON t.course = :course WHERE q.topic = t";

        TypedQuery<Question> query = em.createQuery(queryString, Question.class);
        query.setParameter("course", course );

        return query.getResultList();

    }

    /**
     * Query Questions by a given course and topic
     *
     * @return List<Topic>
     *
     */
    public List<Question> queryTopicQuestions(String coursename, String topicname) {

        Course course = queryCourseByName(coursename);
        Topic topic = this.queryTopicByName(topicname);

        EntityManager em = EMF.createEntityManager();
        String queryString = "SELECT q FROM question q JOIN topic t ON t.course = :course WHERE q.topic = t AND q.topic = :topic";

        TypedQuery<Question> query = em.createQuery(queryString, Question.class);
        query.setParameter("course", course ).setParameter("topic", topic);

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

    /**
     * Query a Topic by name
     *
     * @return Topic
     *
     */
    public Topic queryTopicByName(String topicname) {

        EntityManager em = EMF.createEntityManager();

        String queryString = "FROM " +  Topic.class.getName() + " WHERE topicName =:name";

        // an dieser Stelle kann es eine Exception geben todo: abfangen
        TypedQuery<Topic> query = em.createQuery(queryString, Topic.class);
        query.setParameter( "name", topicname );

        return query.getSingleResult();
    }

}

