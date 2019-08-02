package edu.hsalbsig.swarch.justintime.api;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.hsalbsig.swarch.justintime.dto.PermissionDto;
import edu.hsalbsig.swarch.justintime.exception.EntityNotFoundException;
import edu.hsalbsig.swarch.justintime.service.MilestoneService;
import edu.hsalbsig.swarch.justintime.service.ProjectService;

@Path("/permissions")
public class PermissionController {

	@EJB
	private ProjectService projectService;
	@EJB
	private MilestoneService milestoneService;

	@GET
	@Path("/write")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWritePermission(@QueryParam("type") final String objectType,
			@QueryParam("id") final int objectId) throws EntityNotFoundException {

		var dto = new PermissionDto(objectType, objectId, "WRITE", false);

		var hasAccess = false;

		switch (objectType) {
		case "project":
			hasAccess = projectService.hasWriteAccess(objectId);
			break;
		case "milestone":
			hasAccess = milestoneService.hasWriteAccess(objectId);
			break;
		default:
			break;
		}

		dto.setHasPermission(hasAccess);

		return Response.status(Status.OK).entity(dto).build();
	}
}
