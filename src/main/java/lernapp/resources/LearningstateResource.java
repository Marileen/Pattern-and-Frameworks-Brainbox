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

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Path("/state")
public class LearningstateResource {

    UserService userService = new UserService();
    QuestionService questionService = new QuestionService();
    LearningStateService lsService = new LearningStateService();

    public LearningstateResource() {
    }


    /*
     *
     * Fragen zu einem bestimmten LearningState eines Users - todo: umlagern in QuestionResource
     */
    @JwtFilter.JwtNeeded
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{userId}/{stateId}")
    public List<Question> getMarkedQuestions (@PathParam("userId") Long userId, @PathParam("stateId") Long stateId ) {

        //todo: gucken ob der eingeloggte user auch der ist, für den die Fragen geholt werden
        return lsService.queryQuestionsForLearningStateAndUser(userId, stateId);

    }

    /*
     *
     * Learningstate zu einem User und einer Question abrufen
     */
    @JwtFilter.JwtNeeded
    @Path("/{userId}/{questionId}")
    @GET
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    //public LearningState getLS (User user, Question question) {
    public Response getLS (@PathParam("userId") Long userId, @PathParam("questionId") Long qId) {

        //Object that we want to return when found
        LearningState learningState;

        try {

            Question question = questionService.queryById(qId);
            User user = userService.queryById(userId);

            //schauen, ob es einen state zu der Frage und dem user gibt
            try {
                learningState = lsService.queryLearningState(user, question);
                return Response.ok().entity(learningState).build();
            } catch (Exception e) {
                //no content
                return Response.status(204, "No State found for User and Question").build();
            }

        } catch (Exception e) {
            return Response.status(400, "Requested User or Question not found").build();
        }
    }


    /*
     *
     * Setzen eines Status für User und Question
     */
    @JwtFilter.JwtNeeded
    @Path("set")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response setLS (String userQuestionLS, @Context UriInfo uriInfo) {


        //parse JSON with Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;

        Question questionRef;
        User loggedInUser;

        //read JSON like DOM Parser
        try {
            rootNode = objectMapper.readTree(userQuestionLS);
        } catch (IOException e) {
            return Response.status(400, "invalid json").build();
        }

        try {
            JsonNode questionNode = rootNode.path("question");
            // exception hier wenn key question nicht im json
            Question questionFromJson = objectMapper.treeToValue(questionNode, Question.class);
            questionRef = questionService.queryById(questionFromJson.getQuestionID());

        } catch (Exception e) {
            return Response.status(400, "json must provide an object with key 'question' which matches a Question " +
                    "and has at least a questionID field").build();
        }

        try {

            JsonNode userNode = rootNode.path("user");
            // exeption hier wenn kein "user" im json
            User userFromJson = objectMapper.treeToValue(userNode, User.class);
            //loggedInUser = userService.queryByCredentials(userFromJson.email, userFromJson.password);
            loggedInUser = userService.queryById(userFromJson.getUserID());

        } catch (Exception e) {
            return Response.status(400, "json must provide an object with key 'user' which matches a User " +
                    "and has at least a userID field").build();
        }

        try {

            JsonNode lsNode = rootNode.path("learningState");
            // exeption hier wenn kein "learningState" im json
            LearningState lsFromJson = objectMapper.treeToValue(lsNode, LearningState.class);
            LearningState lsRef = lsService.queryById(lsFromJson.learningStateID);


            //save or update
            try {
                lsService.save(new UserQuestionLS(loggedInUser, questionRef, lsRef));

                URI uri = uriInfo.getAbsolutePathBuilder().path("test").build();
                return Response.created(uri).build(); // 201
            } catch (Exception e) {
                return Response.status(400, "ungültiger Wert in einem der Felder wurde übermittelt. Message: " + e.getCause().getCause()).build();
            }

        } catch (Exception e) {
            return Response.status(400, "json must provide an object with key 'learningState' which matches a Learningstate").build();
        }

    }

}
