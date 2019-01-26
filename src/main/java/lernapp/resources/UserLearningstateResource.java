package lernapp.resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lernapp.filter.JwtFilter;
import lernapp.model.LearningState;
import lernapp.model.Question;
import lernapp.model.User;
import lernapp.model.UserQuestionLS;
import lernapp.service.LearningStateService;
import lernapp.service.QuestionService;
import lernapp.service.UserService;

import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.net.URI;

/**
 * LearningState endpoint for a certain user (REST API)
 *
 */

@Path("/user/{userId}/state")
public class UserLearningstateResource {

    UserService userService = new UserService();
    QuestionService questionService = new QuestionService();
    LearningStateService lsService = new LearningStateService();

    public UserLearningstateResource() {
    }


    // fetches LearningState for a certain user and a certain question
    @JwtFilter.JwtNeeded
    @Path("/question/{questionId}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public Response getLS (@PathParam("userId") Long userId, @PathParam("questionId") Long qId) {

        // Object that we want to return when found
        LearningState learningState;

        try {
            Question question = questionService.queryById(qId);
            User user = userService.queryById(userId);

            // checks if there is a LearningState for this question and this user
            learningState = lsService.queryLearningState(user, question);
            return Response.ok().entity(learningState).build();

        }  catch (NoResultException e) {
            // returns status code 404 - not found
            return Response.status(404, "No State found for User and Question").build();

        } catch (Exception e) {
            // returns status code 400
            return Response.status(400, "Requested User or Question not found").build();
        }
    }


    // sets LearningState for a certain user an a certain question:
    @JwtFilter.JwtNeeded
    @Path("/question/{questionId}")
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putLearningStage (LearningState learningState, @PathParam("userId") Long userId, @PathParam("questionId") Long qId, @Context UriInfo uriInfo) {

        // save or update new LearningState for user and question
        try {
            Question question = questionService.queryById(qId);
            User user = userService.queryById(userId);

            //find Learningstate
            LearningState lsRef = lsService.queryById(learningState.getLearningStateID());

            UserQuestionLS uqls = new UserQuestionLS(user, question, lsRef);
            lsService.save(uqls);

            // Response status depends on whether an insert or an update has been executed
            return Response.ok().entity(lsRef).build(); // produces status code 200


        } catch (Exception e) {
            return Response.status(500, e.getMessage()).build();
        }
    }



    // OLD VERSION that sets a LearningState for a certain user an a certain question:

    // We leave this here for now because it is already used in our frontend apps
    // This is a Learning during the development of the concepts of the restful pinciple,
    // that it is not about thinking of operations (set, get) but about thinking
    // of objects (user and question that builds together an unique key)

    @JwtFilter.JwtNeeded
    @Path("set")
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response setLS (String userQuestionLS, @Context UriInfo uriInfo) {

        // parse JSON with Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;

        Question questionRef;
        User loggedInUser;

        try { // checks if JSON is valid JSON
            rootNode = objectMapper.readTree(userQuestionLS); // read JSON like DOM Parser
        } catch (IOException e) {
            return Response.status(400, "invalid json").build();
        }

        try { // checks if the key "question" exists in JSON
            JsonNode questionNode = rootNode.path("question");
            Question questionFromJson = objectMapper.treeToValue(questionNode, Question.class);
            questionRef = questionService.queryById(questionFromJson.getQuestionID());
        } catch (Exception e) {
            return Response.status(400, "json must provide an object with key 'question' which matches a Question " +
                    "and has at least a questionID field").build();
        }

        try { // checks if the key "user" exists in JSON
            JsonNode userNode = rootNode.path("user");
            User userFromJson = objectMapper.treeToValue(userNode, User.class);
            loggedInUser = userService.queryById(userFromJson.getUserID());
        } catch (Exception e) {
            return Response.status(400, "json must provide an object with key 'user' which matches a User " +
                    "and has at least a userID field").build();
        }

        try { // checks if the key "learningState" exists in JSON
            JsonNode lsNode = rootNode.path("learningState");
            LearningState lsFromJson = objectMapper.treeToValue(lsNode, LearningState.class);
            LearningState lsRef = lsService.queryById(lsFromJson.getLearningStateID());

            // save or update new LearningState
            try {
                lsService.save(new UserQuestionLS(loggedInUser, questionRef, lsRef));

                //URI uri = uriInfo.getAbsolutePathBuilder().path(product.id.toString()).build();
                URI uri = uriInfo.getAbsolutePathBuilder().path("test").build();
                return Response.created(uri).build(); // produces status code 201

            } catch (Exception e) {
                return Response.status(400, "ungültiger Wert in einem der Felder wurde übermittelt. Message: " + e.getCause().getCause()).build();
            }

        } catch (Exception e) {
            return Response.status(400, "json must provide an object with key 'learningState' which matches a Learningstate").build();
        }
    }

}
