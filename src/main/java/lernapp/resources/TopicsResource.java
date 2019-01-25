package lernapp.resources;

import lernapp.filter.AdminOnlyFilter;
import lernapp.filter.JwtFilter;
import lernapp.model.Topic;
import lernapp.service.TopicService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Topics endpoint (REST API)
 *
 */

@Path("/topics")
public class TopicsResource {

    TopicService topicService = new TopicService();
    public TopicsResource() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Topic> getTopics() {
        return topicService.queryAll();
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{coursename}") // fetches topics for a certain course e. g. /topics/Computergrafik
    public List<Topic> getCourseTopics(@PathParam("coursename") String coursename) {
        List list = topicService.queryCourseTopics(coursename);
        return list;
    }

    @POST
    @JwtFilter.JwtNeeded
    @AdminOnlyFilter.AdminOnly                 //only admin users are allowed to create Topics
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response postTopic(Topic topic, @Context UriInfo uriInfo) {

        try {
            // Topic is persisted with a new generated id, which is returned in the response location URI
            topicService.save(topic);
            URI uri = uriInfo.getAbsolutePathBuilder().path(topic.toString()).build();

            return Response.created(uri).entity(topic).build(); // / produces status code 201

        } catch (Exception e) {
            return Response.status(500, e.getMessage()).build();
        }

    }

    @DELETE
    @JwtFilter.JwtNeeded
    @AdminOnlyFilter.AdminOnly      //only admin users are allowed to delete Topics
    @Path("/{id}")
    public boolean deleteTopic(@PathParam("id") Long id) {

        try {
            topicService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
