package lernapp.resources;

import lernapp.service.LearningStateService;
import lernapp.service.QuestionService;
import lernapp.service.UserService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("/state")
public class LearningstateResource {

    UserService userService = new UserService();
    QuestionService questionService = new QuestionService();
    LearningStateService lsService = new LearningStateService();

    public LearningstateResource() {
    }

    /*
     *
     * Bild zu einem LearningState
     */
    @GET
    @Path("{stateId}/image")
    public Response getLSImage() {

        //LearningState ls =

        InputStream image = LearningstateResource.class.getResourceAsStream("/media/Icon_Dartscheibe.jpg");
        return Response.ok(image, new MediaType("image", "jpeg")).build();
    }

}
