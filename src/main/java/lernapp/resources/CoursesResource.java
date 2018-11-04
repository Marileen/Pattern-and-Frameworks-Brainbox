package lernapp.resources;

import lernapp.service.QuestionService;
import lernapp.model.Course;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.*;

// ist dann unter der url im Browser aufzurufen

@Path("/courses")
public class CoursesResource {

    QuestionService questionService = new QuestionService();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Course> getCourses() {
        return questionService.queryAll(Course.class);
    }


}
