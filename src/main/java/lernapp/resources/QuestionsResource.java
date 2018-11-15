package lernapp.resources;

import lernapp.model.Question;
import lernapp.model.Topic;
import lernapp.service.QuestionService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
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
    @Path("/{coursename}")
    public List<Question> getCourseQuestions(@PathParam("coursename") String coursename) {
        List list = questionService.queryCourseQuestions(coursename);
        return list;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{coursename}/{topicname}")
    public List<Question> getTopicQuestions(@PathParam("coursename") String coursename, @PathParam("topicname") String topicname) {
        List list = questionService.queryTopicQuestions(coursename, topicname);
        return list;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response postQuestion(Question question, @Context UriInfo uriInfo) {

        // Question is persisted with a new generated id,
        // The Question entity is returned in the response location URI

        questionService.save(question);
        URI uri = uriInfo.getAbsolutePathBuilder().path(question.toString()).build();
        return Response.created(uri).entity(question).build(); // 201

    }

}
