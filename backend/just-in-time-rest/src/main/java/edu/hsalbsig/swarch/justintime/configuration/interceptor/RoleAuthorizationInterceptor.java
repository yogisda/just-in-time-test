package edu.hsalbsig.swarch.justintime.configuration.interceptor;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import edu.hsalbsig.swarch.justintime.configuration.AdministratorOnly;
import edu.hsalbsig.swarch.justintime.configuration.UserPrincipalBean;

@Provider
@Priority(110)
@AdministratorOnly
public class RoleAuthorizationInterceptor implements ContainerRequestFilter {

    @Inject
    private UserPrincipalBean principal;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (principal.getRole() != 2) {
            requestContext.abortWith(Response.status(Status.FORBIDDEN).build());
        }
    }

}
