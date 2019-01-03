package lernapp.resources;

import lernapp.model.LearningState;
import lernapp.service.LearningStateService;
import lernapp.service.QuestionService;
import lernapp.service.UserService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

@Path("/state")
public class LearningstateResource {

    UserService userService = new UserService();
    QuestionService questionService = new QuestionService();
    LearningStateService lsService = new LearningStateService();

    public LearningstateResource() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<LearningState> getCourses() {
        return lsService.queryAll();
    }

    /*
     *
     * Bild zu einem LearningState
     */
    @GET
    @Path("{stateId}/image")
    public Response getLSImage(@PathParam("stateId") Long stateId) {

        LearningState ls = lsService.queryById(stateId);

        //InputStream image = LearningstateResource.class.getResourceAsStream("/media/Icon_Dartscheibe.jpg");
        InputStream image = LearningstateResource.class.getResourceAsStream("/media/" + ls.getImageUrl());
        return Response.ok(image, new MediaType("image", "jpeg")).build();
    }

}
