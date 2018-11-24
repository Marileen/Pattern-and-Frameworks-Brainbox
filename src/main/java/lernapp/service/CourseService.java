package lernapp.service;

import lernapp.model.Course;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class CourseService extends BasicService<Course> {

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

}

