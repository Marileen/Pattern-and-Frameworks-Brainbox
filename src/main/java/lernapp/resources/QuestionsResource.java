package lernapp.resources;

import lernapp.filter.AdminOnlyFilter;
import lernapp.filter.JwtFilter;
import lernapp.model.Question;
import lernapp.service.LearningStateService;
import lernapp.service.QuestionService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Questions endpoint (REST API)
 *
 */

@Path("/questions")    
public class QuestionsResource {

    QuestionService questionService = new QuestionService();
    LearningStateService lsService = new LearningStateService();

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
    @Path("/{coursename}") // fetches all questions for a certain course e. g. /questions/Datenbanken
    public List<Question> getCourseQuestions(@PathParam("coursename") String coursename) {
        List list = questionService.queryCourseQuestions(coursename);
        return list;
    }

    @GET
    // @JwtFilter.JwtNeeded
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{coursename}/{topicname}") // fetches all questions for a topic, e. g. questions/Datenbanken/Wissensfragen
    public List<Question> getTopicQuestions(@PathParam("coursename") String coursename, @PathParam("topicname") String topicname) {
        List list = questionService.queryTopicQuestions(coursename, topicname);
        return list;
    }

    @JwtFilter.JwtNeeded
    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    @Path("/user/{userId}/state/{stateId}") // fetches questions for a certain LearningState of a User
    public List<Question> getMarkedQuestions (@PathParam("userId") Long userId, @PathParam("stateId") Long stateId ) {

        //todo: gucken ob der eingeloggte user auch der ist, für den die Fragen geholt werden
        return lsService.queryQuestionsForLearningStateAndUser(userId, stateId);
    }

    @POST
    @JwtFilter.JwtNeeded
    @AdminOnlyFilter.AdminOnly
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response postQuestion(Question question, @Context UriInfo uriInfo) {

        // Question is persisted with a new generated id,
        // The Question entity is returned in the response location URI
        try {
            questionService.save(question);
            URI uri = uriInfo.getAbsolutePathBuilder().path(question.toString()).build();
            return Response.created(uri).entity(question).build(); // produces status code 201
        } catch (Exception e) {
            return Response.status(500, e.getMessage()).build();
        }
    }

    @DELETE
    @JwtFilter.JwtNeeded
    @AdminOnlyFilter.AdminOnly
    @Path("/{id}")
    public boolean deleteQuestion(@PathParam("id") Long id) {

        try {
            questionService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



}
