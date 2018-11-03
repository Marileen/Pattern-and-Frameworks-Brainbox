package lernapp.resources;

import lernapp.model.Topic;
import lernapp.service.QuestionService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

// ist dann unter der url im Browser aufrufbar
@Path("/topics")
public class TopicsResource {

    QuestionService questionService = new QuestionService();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Topic> getTopics() {
        return questionService.queryAll(Topic.class);
    }

    @Path("/{coursename}/{topicname}")
    public List<Topic> getCourseTopics() {
        return questionService.queryAll(Topic.class);
    }

}
