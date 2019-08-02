package edu.hsalbsig.swarch.justintime.configuration.interceptor;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import edu.hsalbsig.swarch.justintime.configuration.MatchId;
import edu.hsalbsig.swarch.justintime.configuration.UserPrincipalBean;

@MatchId
@Provider
@Priority(120)
public class PathAuthorizationInterceptor implements ContainerRequestFilter {

    @Inject
    private UserPrincipalBean principal;
    
    @Context
    private ResourceInfo resourceInfo;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (principal.getRole() == 2) {
            return;
        }
        
        try {
            Method method = resourceInfo.getResourceMethod();

            if (method == null) {
                throw new Exception();
            }
            
            MatchId annotation = method.getAnnotation(MatchId.class);
            
            var pathParam = annotation.value();
                
            var pathParams = requestContext.getUriInfo().getPathParameters();
            
            if (!pathParams.containsKey(pathParam)) {
                throw new Exception();
            }
            
            var value = Integer.parseInt(pathParams.get(pathParam).get(0));
            
            if (principal.getId() != value) {
                requestContext.abortWith(Response.status(Status.FORBIDDEN).build());
            }
            
        } catch (Exception ex) {
            requestContext.abortWith(Response.status(Status.INTERNAL_SERVER_ERROR).build());
        }
    }

}
