package lernapp.resources;

import lernapp.service.CourseService;
import lernapp.model.Course;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

// ist dann unter der url im Browser aufzurufen localhost:8050/courses
@Path("/courses")
public class CoursesResource {

    CourseService courseService = new CourseService();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Course> getCourses() {
        return courseService.queryAll(Course.class);
    }


}
