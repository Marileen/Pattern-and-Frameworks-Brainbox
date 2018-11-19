package lernapp.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import lernapp.model.User;
import lernapp.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.security.SecureRandom;
import java.util.List;

@Path("/user")    // ist dann unter der url im Browser aufrufbar
public class UsersResource {

    UserService userService = new UserService();

    public UsersResource() {
    }

    @Path("login")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public User login(User user) {
        User loggedInUser = userService.queryByCredentials(user.email, user.password);

        if (loggedInUser != null) {
            try {
                // Map user object to JSON
                ObjectMapper mapper = new ObjectMapper(); // comes with Jackson
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                String json = mapper.writeValueAsString(loggedInUser);

                // Create an HMAC-protected JWS object with user data as payload
                JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(json));

                /* Use a secret key for HS256
                byte[] secret = AuthenticationFilter.SECRET.getBytes();
                */

                // 256-bit key for HS256 which must be pre-shared
                byte[] sharedKey = new byte[32];
                new SecureRandom().nextBytes(sharedKey);

                // MAC - Message Authentication Code = Signatur
                // Apply the HMAC to the JWS object and send the token to the client
                jwsObject.sign(new MACSigner(sharedKey));
                loggedInUser.jsonWebToken = jwsObject.serialize();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return loggedInUser;
    }

}
