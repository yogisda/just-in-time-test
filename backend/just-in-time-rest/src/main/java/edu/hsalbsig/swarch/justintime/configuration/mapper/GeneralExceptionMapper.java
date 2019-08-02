package edu.hsalbsig.swarch.justintime.configuration.mapper;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		Logger log = Logger.getLogger("GeneralExceptionMapper");

		exception.printStackTrace();
		log.log(Level.SEVERE, exception.getClass().toString());

		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

}
