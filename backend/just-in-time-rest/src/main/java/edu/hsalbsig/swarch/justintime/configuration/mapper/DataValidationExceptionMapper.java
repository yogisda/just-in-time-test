package edu.hsalbsig.swarch.justintime.configuration.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.hsalbsig.swarch.justintime.dto.DataValidationException;

@Provider
public class DataValidationExceptionMapper implements ExceptionMapper<DataValidationException> {

    @Override
    public Response toResponse(DataValidationException exception) {
        return Response.status(Status.BAD_REQUEST).build();
    }

}
