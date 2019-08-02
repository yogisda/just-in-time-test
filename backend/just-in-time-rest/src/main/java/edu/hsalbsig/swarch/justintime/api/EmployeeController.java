package edu.hsalbsig.swarch.justintime.api;

import java.time.ZoneId;
import java.util.Date;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import edu.hsalbsig.swarch.justintime.configuration.AdministratorOnly;
import edu.hsalbsig.swarch.justintime.configuration.MatchId;
import edu.hsalbsig.swarch.justintime.configuration.UserPrincipalBean;
import edu.hsalbsig.swarch.justintime.dto.CredentialsDto;
import edu.hsalbsig.swarch.justintime.dto.DataValidationException;
import edu.hsalbsig.swarch.justintime.dto.EmployeeRegistrationDto;
import edu.hsalbsig.swarch.justintime.dto.EmployeeSimpleDto;
import edu.hsalbsig.swarch.justintime.dto.TokenDto;
import edu.hsalbsig.swarch.justintime.exception.EntityNotFoundException;
import edu.hsalbsig.swarch.justintime.exception.InvalidCredentialsException;
import edu.hsalbsig.swarch.justintime.exception.NoneOfYourBusinessException;
import edu.hsalbsig.swarch.justintime.service.EmployeeService;

@Path("/employees")
public class EmployeeController {
	@EJB
	private EmployeeService employeeService;

	@Inject
	private UserPrincipalBean principal;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(final EmployeeRegistrationDto employee) throws Exception {
		employee.validate();

		var result = employeeService.createEmployee(employee);

		return Response.status(Status.CREATED).entity(result).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response read(@QueryParam("expandRole") final boolean expandRole) throws Exception {
		return Response.status(Status.OK).entity(employeeService.getAllEmployees(expandRole)).build();
	}

	@GET
	@Path("/{id}")
	@MatchId
	@Produces(MediaType.APPLICATION_JSON)
	public Response read(@PathParam("id") final int employeeId) throws Exception {
		return Response.status(Status.OK).entity(employeeService.getEmployeeById(employeeId)).build();
	}

	@PUT
	@Path("/{id}")
	@MatchId
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") final int employeeId, final EmployeeSimpleDto employee) throws Exception {
		employee.validate();

		if (principal.getId() != employeeId && principal.getRole() != 2) {
			throw new NoneOfYourBusinessException();
		}

		return Response.status(Status.OK).entity(employeeService.updateEmployee(employeeId, employee)).build();
	}

	@DELETE
	@Path("/{id}")
	@AdministratorOnly
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") final int employeeId) throws EntityNotFoundException {
		employeeService.deleteEmployee(employeeId);
		return Response.status(Status.OK).build();
	}

	@GET
	@MatchId
	@Path("/{id}/timebookings")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTimebookings(@PathParam("id") final int employeeId) throws EntityNotFoundException {
		return Response.status(Status.OK).entity(employeeService.getTimebookingsForEmployee(employeeId)).build();
	}

	@GET
	@MatchId
	@Path("/{id}/projects")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProjects(@PathParam("id") final int employeeId) throws EntityNotFoundException {
		return Response.status(Status.OK).entity(employeeService.getProjectsForEmployee(employeeId)).build();
	}

	@PUT
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(final CredentialsDto credentials)
			throws InvalidCredentialsException, DataValidationException {
		credentials.validate();

		var employee = employeeService.checkEmployeeCredentials(credentials);

		return Response.status(Status.OK).entity(generateToken(employee)).build();
	}

	private TokenDto generateToken(EmployeeSimpleDto employee) {
		Algorithm algorithm = Algorithm.HMAC256("vollTollerSchluessel");

		String token = JWT.create().withIssuer("Justin-Time").withClaim("role", employee.getRoleId())
				.withClaim("id", employee.getId())
				.withExpiresAt(Date.from(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
						.plusDays(1).atZone(ZoneId.systemDefault()).toInstant()))
				.sign(algorithm);

		return new TokenDto(token);
	}
}
