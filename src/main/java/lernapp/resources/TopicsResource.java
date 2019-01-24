package lernapp.resources;

import lernapp.model.Topic;
import lernapp.service.TopicService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

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
}
