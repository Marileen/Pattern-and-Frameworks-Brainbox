package lernapp.service;

import lernapp.model.Course;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class CourseService extends BasicService<Course> {

    public CourseService() {
        super(Course.class);
    }

    /**
     * Query a Course by name
     * @param courseName
     * @return Course
     *
     */
    public Course queryCourseByName(String courseName) {

        EntityManager em = EMF.createEntityManager();

        String queryString = "FROM " +  Course.class.getName() + " WHERE courseName =:cName";

        // no try... catch here in order to keep genuine error message
        TypedQuery<Course> query = em.createQuery(queryString, Course.class);
        query.setParameter( "cName", courseName );

        Course singleResult = query.getSingleResult();
        em.close();
        return singleResult;
    }
}

