package edu.hsalbsig.swarch.justintime.configuration.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.hsalbsig.swarch.justintime.exception.NoneOfYourBusinessException;

@Provider
public class NoneOfYourBusinessExceptionMapper implements ExceptionMapper<NoneOfYourBusinessException> {
    @Override
    public Response toResponse(NoneOfYourBusinessException exception) {
        return Response.status(Status.FORBIDDEN).build();
    }
}
