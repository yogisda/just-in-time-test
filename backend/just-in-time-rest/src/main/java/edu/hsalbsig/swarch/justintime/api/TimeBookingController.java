package edu.hsalbsig.swarch.justintime.api;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import edu.hsalbsig.swarch.justintime.dto.TimeBookingInputDto;
import edu.hsalbsig.swarch.justintime.service.ProjectService;
import edu.hsalbsig.swarch.justintime.service.TimeBookingService;

@Path("/timebookings")
public class TimeBookingController {
	@EJB
	private TimeBookingService timeBookingService;
	@EJB
	private ProjectService projectService;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Integer id) throws Exception {
		ResponseBuilder responseBuilder = null;

		responseBuilder = Response.status(Status.OK).entity(timeBookingService.findById(id));

		return responseBuilder.build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(TimeBookingInputDto timeBookingDto) throws Exception {
		ResponseBuilder responseBuilder = null;
		responseBuilder = Response.status(Status.OK).entity(timeBookingService.create(timeBookingDto));

		return responseBuilder.build();
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") final Integer id, TimeBookingInputDto timeBookingDto) throws Exception {
		ResponseBuilder responseBuilder = null;
		timeBookingDto.setId(id);
		responseBuilder = Response.status(Status.OK).entity(timeBookingService.update(timeBookingDto));

		return responseBuilder.build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") final Integer id) throws Exception {
		ResponseBuilder responseBuilder = null;

		timeBookingService.delete(id);
		responseBuilder = Response.status(Status.OK);

		return responseBuilder.build();
	}
}
