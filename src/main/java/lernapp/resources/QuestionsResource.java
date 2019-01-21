package lernapp.resources;

import lernapp.filter.AdminOnlyFilter;
import lernapp.filter.JwtFilter;
import lernapp.model.Question;
import lernapp.service.QuestionService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;


@Path("/questions")
public class QuestionsResource {

    QuestionService questionService = new QuestionService();

    public QuestionsResource() {
    }

    @GET
    @JwtFilter.JwtNeeded
    @Produces({MediaType.APPLICATION_JSON})
    public List<Question> getQuestions() {
        return questionService.queryAll();
    }

    @GET
    @JwtFilter.JwtNeeded
    @Produces({MediaType.APPLICATION_JSON})
    //@Path("course/{coursename}")
    @Path("/{coursename}")
    public List<Question> getCourseQuestions(@PathParam("coursename") String coursename) {
        List list = questionService.queryCourseQuestions(coursename);
        return list;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{coursename}/{topicname}")
    public List<Question> getTopicQuestions(@PathParam("coursename") String coursename, @PathParam("topicname") String topicname) {
        List list = questionService.queryTopicQuestions(coursename, topicname);
        return list;
    }

    @POST
    @JwtFilter.JwtNeeded
    @AdminOnlyFilter.AdminOnly
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response postQuestion(Question question, @Context UriInfo uriInfo) {

        // Question is persisted with a new generated id,
        // The Question entity is returned in the response location URI

        //todo: try catch hinzufügen
        questionService.save(question);
        URI uri = uriInfo.getAbsolutePathBuilder().path(question.toString()).build();
        return Response.created(uri).entity(question).build(); // 201

    }

    @DELETE
    @JwtFilter.JwtNeeded
    @AdminOnlyFilter.AdminOnly
    @Path("/{id}")
    public boolean deleteQuestion(@PathParam("id") Long id) {

        // Question will be deleted
        try {
            questionService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
