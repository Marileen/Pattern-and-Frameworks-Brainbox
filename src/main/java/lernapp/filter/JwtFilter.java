package lernapp.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import lernapp.filter.JwtFilter.JwtNeeded;
import lernapp.model.User;

import javax.annotation.Priority;
import javax.ws.rs.NameBinding;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@JwtNeeded
@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter {

        public static final String SECRET = "lernen7ma8chtuns5richtig77s5pass";

        @NameBinding  // ToDo: recherchieren
        @Retention(RetentionPolicy.RUNTIME) // ToDo: recherchieren
        public @interface JwtNeeded {
        }

        @Context // ToDO: recherchieren
        private UriInfo uriInfo;

        @Override
        public void filter(ContainerRequestContext request) throws IOException {

            // Get the HTTP Authorization header from the request
            String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            // Check if the HTTP Authorization header is present and formatted correctly
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }

            // Extract the token from the HTTP Authorization header
            String jsonWebToken = authorizationHeader.substring("Bearer".length()).trim();

            if (jsonWebToken != null) {

                // Authentication: Check signed JSON Web Token (JWT)
                try {
                    JWSObject jwsObject = JWSObject.parse(jsonWebToken);
                    JWSVerifier verifier = new MACVerifier(SECRET); // ToDo was genau macht der MACVerifier?

                    if (jwsObject.verify(verifier)) {
                        String payload = jwsObject.getPayload().toString();
                        User user = new ObjectMapper().readValue(payload, User.class);
                        System.out.println("User verified: " + user.firstname);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
}

