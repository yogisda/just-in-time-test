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
import javax.ws.rs.core.Response.Status;

import edu.hsalbsig.swarch.justintime.dto.CustomerDto;
import edu.hsalbsig.swarch.justintime.dto.DataValidationException;
import edu.hsalbsig.swarch.justintime.exception.EntityNotFoundException;
import edu.hsalbsig.swarch.justintime.service.CustomerService;

@Path("/customers")
public class CustomerController {

	@EJB
	private CustomerService customerService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(final CustomerDto customer) throws EntityNotFoundException, DataValidationException {
		var result = customerService.createCustomer(customer);

		return Response.status(Status.CREATED).entity(result).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response read() {
		return Response.status(Status.OK).entity(customerService.getAllCustomers()).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response read(@PathParam("id") final int customerId) throws EntityNotFoundException {
		return Response.status(Status.OK).entity(customerService.getCustomerById(customerId)).build();
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") final int customerId, final CustomerDto customer)
			throws EntityNotFoundException, DataValidationException {
		var result = customerService.updateCustomer(customerId, customer);

		return Response.status(Status.CREATED).entity(result).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") final int customerId) throws EntityNotFoundException {
		customerService.delete(customerId);

		return Response.status(Status.OK).build();
	}

	@GET
	@Path("/{id}/projects")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProjects(@PathParam("id") final int customerId) {
		return "GET /customers/" + Integer.toString(customerId) + "/projects";
	}

}
