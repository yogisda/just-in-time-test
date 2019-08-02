package edu.hsalbsig.swarch.justintime.configuration.interceptor;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;

import edu.hsalbsig.swarch.justintime.configuration.UserPrincipalBean;

@Provider
@Priority(100)
public class AuthenticationInterceptor implements ContainerRequestFilter {
    private static final String AUTHENTICATION_SCHEME = "Bearer";
    
    @Inject
    private UserPrincipalBean userPrincipal;
    
    @Inject
    private JWTVerifier verifier;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (requestContext.getUriInfo().getPath().endsWith("login")) {
            return;
        }
        
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

        try {
            validateToken(token);
        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
        }
    }
    
    private boolean isTokenBasedAuthentication(String authorizationHeader) {
        return authorizationHeader != null
                && authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }

    private void validateToken(String token) throws Exception {
        DecodedJWT jwt = verifier.verify(token);

        var id = jwt.getClaim("id").asInt();
        var role = jwt.getClaim("role").asInt();
        
        if (id == null || role == null) {
            throw new Exception();
        }
        
        userPrincipal.setId(id);
        userPrincipal.setRole(role);
    }

}
