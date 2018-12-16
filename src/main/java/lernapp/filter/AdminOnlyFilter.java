package lernapp.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSObject;
import lernapp.model.User;
import lernapp.service.UserService;
import lernapp.filter.AdminOnlyFilter.AdminOnly;

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


@AdminOnly
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AdminOnlyFilter implements ContainerRequestFilter {

        @NameBinding
        @Retention(RetentionPolicy.RUNTIME)
        public @interface AdminOnly {
        }

        @Context
        private UriInfo uriInfo;

        @Override
        public void filter(ContainerRequestContext request) throws IOException {

            UserService userService = new UserService();

            // Get the HTTP Authorization header from request
            String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            // Check if the HTTP Authorization header is present and formatted correctly
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }

            // Extract the token from the HTTP Authorization header
            String jsonWebToken = authorizationHeader.substring("Bearer".length()).trim();

            if (jsonWebToken != null) {

                //check if user is admin
                try {
                    JWSObject jwsObject = JWSObject.parse(jsonWebToken);

                    String payload = jwsObject.getPayload().toString();
                    User userFromJson = new ObjectMapper().readValue(payload, User.class);

                    User userRef = userService.queryById(userFromJson.getUserID());

                    if (userRef.isAdmin()) {
                        return;
                    }

                    throw new WebApplicationException(Response.Status.FORBIDDEN);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }
}

