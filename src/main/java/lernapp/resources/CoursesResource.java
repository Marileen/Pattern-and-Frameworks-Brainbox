package lernapp.resources;

import lernapp.filter.AdminOnlyFilter;
import lernapp.filter.JwtFilter;
import lernapp.model.Course;
import lernapp.service.CourseService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

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

    @POST
    @JwtFilter.JwtNeeded
    @AdminOnlyFilter.AdminOnly                 //only admin users are allowed to create Courses
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response postCourse(Course course, @Context UriInfo uriInfo) {

        try {

            // Course is persisted with a new generated id,
            // The Course entity is returned in the response location URI
            courseService.save(course);
            URI uri = uriInfo.getAbsolutePathBuilder().path(course.toString()).build();

            return Response.created(uri).entity(course).build(); // 201

        } catch (Exception e) {
            return Response.status(500, e.getMessage()).build();
        }

    }

    @DELETE
    @JwtFilter.JwtNeeded
    @AdminOnlyFilter.AdminOnly      //only admin users are allowed to delete Courses
    @Path("/{id}")
    public boolean deleteCourse(@PathParam("id") Long id) {

        try {
            courseService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
