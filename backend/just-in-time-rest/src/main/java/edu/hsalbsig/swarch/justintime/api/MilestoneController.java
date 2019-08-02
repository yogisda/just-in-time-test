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

import edu.hsalbsig.swarch.justintime.dto.MilestoneDto;
import edu.hsalbsig.swarch.justintime.service.MilestoneService;

@Path("/milestones")
public class MilestoneController {
	@EJB
	private MilestoneService milestoneService;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Integer id) throws Exception {
		ResponseBuilder responseBuilder = null;

		responseBuilder = Response.status(Status.OK).entity(milestoneService.findById(id));

		return responseBuilder.build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(MilestoneDto milestoneDto) throws Exception {
		ResponseBuilder responseBuilder = null;

		responseBuilder = Response.status(Status.OK).entity(milestoneService.create(milestoneDto));

		return responseBuilder.build();
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(MilestoneDto milestoneDto, @PathParam("id") Integer id) throws Exception {
		ResponseBuilder responseBuilder = null;
		milestoneDto.setId(id);
		responseBuilder = Response.status(Status.OK).entity(milestoneService.update(milestoneDto));

		return responseBuilder.build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") final Integer id) throws Exception {
		ResponseBuilder responseBuilder = null;

		milestoneService.delete(id);
		responseBuilder = Response.status(Status.OK);

		return responseBuilder.build();
	}

}
