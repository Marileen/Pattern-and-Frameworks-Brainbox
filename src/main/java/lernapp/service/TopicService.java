package lernapp.service;

import lernapp.model.Course;
import lernapp.model.Question;
import lernapp.model.Topic;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class TopicService extends BasicService<Topic> {

    CourseService courseService = new CourseService();

    /**
     * Query Topics that belongs to a specific Course
     *
     * @return List<Topic>
     *
     */
    public List<Topic> queryCourseTopics(String courseName) {

        Course course = courseService.queryCourseByName(courseName);

        EntityManager em = EMF.createEntityManager();
        String queryString = "FROM "+ Topic.class.getName() + " WHERE course = :courseId";

        TypedQuery<Topic> query = em.createQuery(queryString, Topic.class);
        query.setParameter("courseId", course );

        return query.getResultList();
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

