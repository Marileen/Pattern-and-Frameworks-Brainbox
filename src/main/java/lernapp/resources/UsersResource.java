package lernapp.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import lernapp.filter.JwtFilter;
import lernapp.model.LearningState;
import lernapp.model.Question;
import lernapp.model.User;
import lernapp.service.LearningStateService;
import lernapp.service.QuestionService;
import lernapp.service.UserService;
import org.hibernate.exception.ConstraintViolationException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")    // ist dann unter der url im Browser aufrufbar
public class UsersResource {

    UserService userService = new UserService();
    QuestionService questionService = new QuestionService();
    LearningStateService lsService = new LearningStateService();

    public UsersResource() {
    }

    @JwtFilter.JwtNeeded
    @Path("state")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    //public LearningState getLS (User user, Question question) {
    public LearningState getLS (String questionAndUser) {

        //parse JSON with Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        LearningState emptyLearningState = new LearningState();

        //read JSON like DOM Parser
        try {
            // fuer JSON-Obj mit mehr als einem Objekt braucht man reedTree
            JsonNode rootNode = objectMapper.readTree(questionAndUser);

            JsonNode questionNode = rootNode.path("q");
            // exeption hier wenn q erwartet, aber gibt es nicht
            Question questionFromJson = objectMapper.treeToValue(questionNode, Question.class);
            Question questionRef =questionService.queryById(questionFromJson.getQuestionID(), Question.class);

            JsonNode userNode = rootNode.path("user");
            // exeption hier wenn kein "user" im json
            User userFromJson = objectMapper.treeToValue(userNode, User.class);
            User loggedInUser = userService.queryByCredentials(userFromJson.email, userFromJson.password);


            // Question über post daten
            return lsService.queryLearningState(loggedInUser, questionRef);

        } catch (Exception e) {
            return emptyLearningState;
        }
    }

    @Path("login")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public User login(User user) {
        // mit queryBy... prüfen wir, ob diese Email & passwort in der DB vorhanden ist
        User loggedInUser = userService.queryByCredentials(user.email, user.password);

        if (loggedInUser != null) {
            try {
                // Map user object to JSON
                ObjectMapper mapper = new ObjectMapper(); // comes with Jackson
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                String json = mapper.writeValueAsString(loggedInUser);

                // Create an HMAC-protected JWS object with user data as payload
                JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(json));

                // Use a secret key for HS256
                byte[] secret = JwtFilter.SECRET.getBytes();

                // MAC - Message Authentication Code = Signatur
                // Apply the HMAC to the JWS object and send the token to the client
                jwsObject.sign(new MACSigner(secret));
                loggedInUser.jsonWebToken = jwsObject.serialize();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return loggedInUser;
    }

    @Path("register")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response register(User user) {

        ObjectMapper objectMapper = new ObjectMapper();

        if ( isNullOrEmpty(user.email) ) {
            //zurückgeben dass email nicht leer sein darf
            return Response.status(400, "Email field should be provided").build();
        }

        if ( isNullOrEmpty(user.password) ) {
            //zurückgeben dass pw nicht leer sein darf
            return Response.status(400, "Password field should be provided").build();
        }

        if ( isNullOrEmpty(user.firstname) ) {
            //zurückgeben dass firstname nicht leer sein darf
            return Response.status(400, "Firstname field should be provided").build();
        }

        try {
            // USER SPEICHERN UND EINLOGGEN
            User registeredUser = userService.save(user);
            return Response.ok().entity(login(registeredUser)).build();

        } catch (Exception e) {
            //kann man instanceof rufen, wenn getCause null ist? JA
            if (e.getCause() != null && e.getCause().getCause() instanceof ConstraintViolationException) {
                return Response.status(409, "User with email already exists").build();
            }
            return Response.status(500, e.getMessage()).build();
        }

    }

    public static boolean isNullOrEmpty (String test) {
        return test == null || test.isEmpty();
    }

}
