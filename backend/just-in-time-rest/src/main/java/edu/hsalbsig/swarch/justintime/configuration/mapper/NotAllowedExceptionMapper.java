package edu.hsalbsig.swarch.justintime.configuration.mapper;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotAllowedExceptionMapper implements ExceptionMapper<javax.ws.rs.NotAllowedException> {

	@Override
	public Response toResponse(NotAllowedException exception) {
		return Response.status(Status.NOT_FOUND).build();
	}

}