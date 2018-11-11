package lernapp.resources;

import lernapp.model.Topic;
import lernapp.service.QuestionService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

// ist dann unter der url im Browser aufrufbar
@Path("/topics")
public class TopicsResource {

    QuestionService questionService = new QuestionService();

    public TopicsResource() {

    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Topic> getTopics() {
        return questionService.queryAll(Topic.class);
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{coursename}/{topicname}")
    public List<Topic> getCourseTopics(@PathParam("coursename") String coursename, @PathParam("topicname") String topicname) {
        List list = questionService.queryCourseTopics(coursename, topicname);
        return list;

        //return new TopicsResource();
    }


    /*

    @Path("{orderId}")
	public OrderResource getOrder(@PathParam("orderId") Long orderId) {
		return new OrderResource(customerId, orderId);
	}

*/

}
