package lernapp.resources;

import lernapp.model.Topic;
import lernapp.service.TopicService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/topics")    // ist dann unter der url im Browser aufrufbar
public class TopicsResource {

    TopicService topicService = new TopicService();
    public TopicsResource() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Topic> getTopics() {
        return topicService.queryAll();
    }

    // Beispiel URL: http://localhost:8050/topics/Computergrafik
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{coursename}")
    public List<Topic> getCourseTopics(@PathParam("coursename") String coursename) {
        List list = topicService.queryCourseTopics(coursename);
        return list;
    }

}
