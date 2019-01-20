package lernapp.service;

import lernapp.model.Course;
import lernapp.model.Question;
import lernapp.model.Topic;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class TopicService extends BasicService<Topic> {

    CourseService courseService = new CourseService();

    public TopicService() {
        super(Topic.class);
    }

    /**
     * Query Topics that belongs to a specific Course
     *
     * @param courseName
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
     * Query a Topic by topicname
     *
     * @param topicname
     * @return Topic
     *
     */
    public Topic queryTopicByName(String topicname) {

        EntityManager em = EMF.createEntityManager();

        String queryString = "FROM " +  Topic.class.getName() + " WHERE topicName =:name";

        TypedQuery<Topic> query = em.createQuery(queryString, Topic.class);
        query.setParameter( "name", topicname );

        return query.getSingleResult();
    }

}

