package lernapp.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import lernapp.filter.AdminOnlyFilter;
import lernapp.filter.JwtFilter;
import lernapp.model.User;
import lernapp.service.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User endpoint (REST API)
 *
 */

@Path("/user")
public class UsersResource {

    UserService userService = new UserService();

    public UsersResource() {
    }

    @Path("login")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response login(User user) {

        // checks if there is a user in the database with the given credentials
        User loggedInUser = userService.queryByCredentials(user.getEmail(), user.getPassword());

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

                // MAC - Message Authentication Code = Signature
                // Apply the HMAC to the JWS object and send the token to the client
                jwsObject.sign(new MACSigner(secret));
                loggedInUser.setJsonWebToken(jwsObject.serialize());

                return Response.ok().entity(loggedInUser).build();

            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(500, e.getMessage()).build();
            }
        }

        // user is null because he couldn`t be found by the given credentials
        return Response.status(400, "Credentials incorrect").build();

    }

    @Path("register")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response register(User user) {

        if ( isNullOrEmpty(user.getEmail()) ) {
            // returns status code 400 if email field is blank
            return Response.status(400, "Email field should be provided").build();
        }

        if ( isNullOrEmpty(user.getPassword()) ) {
            // returns status code 400 if password field is blank
            return Response.status(400, "Password field should be provided").build();
        }

        if ( isNullOrEmpty(user.getFirstname()) ) {
            // returns status code 400 if first name field is blank
            return Response.status(400, "Firstname field should be provided").build();
        }

        try {
            // save user and perform login
            User registeredUser = userService.save(user);
            return login(registeredUser);

        } catch (Exception e) {
            // returns status code 409 if user tries to register with existing email (conflict)
            if (e.getCause() != null && e.getCause().getCause() instanceof ConstraintViolationException) {
                return Response.status(409, "User with email already exists").build();
            }
            return Response.status(500, e.getMessage()).build();
        }
    }

    @DELETE
    @JwtFilter.JwtNeeded
    @AdminOnlyFilter.AdminOnly      //only admin users are allowed to delete Users
    @Path("/{id}")
    public boolean deleteUser(@PathParam("id") Long id) {

        try {
            userService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNullOrEmpty (String test) {
        return test == null || test.isEmpty();
    }

}
