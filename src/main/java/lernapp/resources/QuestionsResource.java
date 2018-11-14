package lernapp.resources;

import lernapp.model.Question;
import lernapp.model.Topic;
import lernapp.service.QuestionService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/questions")    // ist dann unter der url im Browser aufrufbar
public class QuestionsResource {

    QuestionService questionService = new QuestionService();

    public QuestionsResource() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Question> getQuestions() {
        return questionService.queryAll(Question.class);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{topicname}")
    public List<Topic> getTopicQuestions(@PathParam("topicname") String topicname) {
        List list = questionService.queryTopicQuestions(topicname);
        return list;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/course/{coursename}")
    public List<Topic> getCourseQuestions(@PathParam("coursename") String coursename) {
        List list = questionService.queryCourseQuestions(coursename);
        return list;
    }


}
