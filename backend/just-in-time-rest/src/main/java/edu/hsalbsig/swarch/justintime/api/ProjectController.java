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

import edu.hsalbsig.swarch.justintime.dto.DataValidationException;
import edu.hsalbsig.swarch.justintime.dto.ProjectDto;
import edu.hsalbsig.swarch.justintime.dto.ProjectMembershipDto;
import edu.hsalbsig.swarch.justintime.exception.EntityNotFoundException;
import edu.hsalbsig.swarch.justintime.service.EmployeeService;
import edu.hsalbsig.swarch.justintime.service.MilestoneService;
import edu.hsalbsig.swarch.justintime.service.ProjectService;
import edu.hsalbsig.swarch.justintime.service.TimeBookingService;

@Path("/projects")
public class ProjectController {
	@EJB
	private ProjectService projectService;
	@EJB
	private MilestoneService milestoneService;
	@EJB
	private TimeBookingService timeBookingService;
	@EJB
	private EmployeeService employeeService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		ResponseBuilder responseBuilder = null;

		responseBuilder = Response.status(Status.OK).entity(projectService.getAllProjects());

		return responseBuilder.build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") final int projectId) throws Exception {
		ResponseBuilder responseBuilder = null;
		ProjectDto projectDto = projectService.findById(projectId);

		responseBuilder = Response.status(Status.OK).entity(projectDto);

		return responseBuilder.build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes((MediaType.APPLICATION_JSON))
	public Response create(ProjectDto projectDto) throws Exception {
		ResponseBuilder responseBuilder = null;
		responseBuilder = Response.status(Status.OK).entity(projectService.create(projectDto));

		return responseBuilder.build();
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes((MediaType.APPLICATION_JSON))
	public Response update(ProjectDto projectDto) throws Exception {
		ResponseBuilder responseBuilder = null;
		responseBuilder = Response.status(Status.OK).entity(projectService.update(projectDto));
		return responseBuilder.build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") final int projectId) throws Exception {
		ResponseBuilder responseBuilder = null;
		projectService.delete(projectId);
		responseBuilder = Response.status(Status.OK);
		return responseBuilder.build();
	}

	@GET
	@Path("/{id}/milestones")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMilestones(@PathParam("id") final int projectId) throws EntityNotFoundException {
		ResponseBuilder responseBuilder = null;
		responseBuilder = Response.status(Status.OK).entity(milestoneService.getMileStonesOfProject(projectId));
		return responseBuilder.build();
	}

	@GET
	@Path("/{id}/timebookings")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTimeBookings(@PathParam("id") final int projectId) {
		ResponseBuilder responseBuilder = null;
		responseBuilder = Response.status(Status.OK).entity(timeBookingService.getAllBookings(projectId));
		return responseBuilder.build();
	}

	@GET
	@Path("/{id}/members")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMembers(@PathParam("id") final int projectId) throws EntityNotFoundException {
		ResponseBuilder responseBuilder = null;

		responseBuilder = Response.status(Status.OK).entity(projectService.getProjectMemberships(projectId));

		return responseBuilder.build();
	}

	@POST
	@Path("/{id}/members")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addMember(@PathParam("id") final Integer projectId, ProjectMembershipDto prjMemShipDto)
			throws DataValidationException, EntityNotFoundException {
		ResponseBuilder responseBuilder = null;
		prjMemShipDto.setProject(projectId);
		responseBuilder = Response.status(Status.OK).entity(projectService.addMember(prjMemShipDto));

		return responseBuilder.build();
	}

	@PUT
	@Path("/{id}/members/{employeeId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMember(@PathParam("id") final Integer projectId,
			@PathParam("employeeId") final Integer employeeId, ProjectMembershipDto prjMemShipDto)
			throws EntityNotFoundException {
		ResponseBuilder responseBuilder = null;
		prjMemShipDto.setEmployee(employeeService.getEmployeeById(employeeId));
		prjMemShipDto.setProject(projectId);
		responseBuilder = Response.status(Status.OK).entity(projectService.updateMember(prjMemShipDto));

		return responseBuilder.build();
	}

	@DELETE
	@Path("/{projectId}/members/{employeeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMember(@PathParam("projectId") final int projectId,
			@PathParam("employeeId") final int employeeId) throws EntityNotFoundException {
		ResponseBuilder responseBuilder = null;

		projectService.deleteMember(projectId, employeeId);
		responseBuilder = Response.status(Status.OK);
		return responseBuilder.build();
	}
}
