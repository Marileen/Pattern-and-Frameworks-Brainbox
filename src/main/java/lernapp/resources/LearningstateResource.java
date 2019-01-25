package lernapp.resources;

import lernapp.filter.AdminOnlyFilter;
import lernapp.filter.JwtFilter;
import lernapp.model.LearningState;
import lernapp.service.LearningStateService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

/**
 * Learningstate endpoint (REST API)
 *
 */

@Path("/state")
public class LearningstateResource {

    LearningStateService lsService = new LearningStateService();

    public LearningstateResource() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<LearningState> getCourses() {
        return lsService.queryAll();
    }


    @GET
    @Path("{stateId}/image")
    public Response getLSImage(@PathParam("stateId") Long stateId) {

        LearningState ls = lsService.queryById(stateId);

        InputStream image = LearningstateResource.class.getResourceAsStream("/media/" + ls.getImageUrl());
        // delivering an image type jpeg
        return Response.ok(image, new MediaType("image", "jpeg")).build();
    }

    @POST
    @JwtFilter.JwtNeeded
    @AdminOnlyFilter.AdminOnly                 //only admin users are allowed to create new State-Types
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response postState(LearningState lsState, @Context UriInfo uriInfo) {

        try {

            // LS is persisted with a new generated id,
            // The LearningState entity is returned in the response location URI
            lsService.save(lsState);
            URI uri = uriInfo.getAbsolutePathBuilder().path(lsState.toString()).build();

            return Response.created(uri).entity(lsState).build(); // 201

        } catch (Exception e) {
            return Response.status(500, e.getMessage()).build();
        }

    }

    @DELETE
    @JwtFilter.JwtNeeded
    @AdminOnlyFilter.AdminOnly      //only admin users are allowed to delete States
    @Path("/{id}")
    public boolean deleteState(@PathParam("id") Long id) {

        try {
            lsService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
