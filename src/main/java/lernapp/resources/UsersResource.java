package lernapp.resources;

import lernapp.model.User;
import lernapp.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/user")    // ist dann unter der url im Browser aufrufbar
public class UsersResource {

    UserService userService = new UserService();

    public UsersResource() {
    }

    @POST
    @JWTTokenNeeded
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUser(id) {
        User user = customerService.queryById(id);
        if (customer == null) {
            return Response.status(404).build(); // 404
        }
        return Response.ok(customer).build();
    }

}
