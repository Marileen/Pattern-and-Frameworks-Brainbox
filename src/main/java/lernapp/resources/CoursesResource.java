package lernapp.resources;

import lernapp.service.CourseService;
import lernapp.model.Course;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Courses endpoint (REST API)
 *
 */

@Path("/courses")
public class CoursesResource {

    CourseService courseService = new CourseService();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Course> getCourses() {
        // calls method from CourseService which inherits this method from BasicService
        return courseService.queryAll();
    }

}
