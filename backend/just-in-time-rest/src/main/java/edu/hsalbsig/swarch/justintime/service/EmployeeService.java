package edu.hsalbsig.swarch.justintime.service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;

import edu.hsalbsig.swarch.justintime.dao.EmployeeDao;
import edu.hsalbsig.swarch.justintime.dao.UserRoleDao;
import edu.hsalbsig.swarch.justintime.dto.CredentialsDto;
import edu.hsalbsig.swarch.justintime.dto.EmployeeDto;
import edu.hsalbsig.swarch.justintime.dto.EmployeeExpandedDto;
import edu.hsalbsig.swarch.justintime.dto.EmployeeRegistrationDto;
import edu.hsalbsig.swarch.justintime.dto.EmployeeSimpleDto;
import edu.hsalbsig.swarch.justintime.dto.ProjectDto;
import edu.hsalbsig.swarch.justintime.dto.TimeBookingDto;
import edu.hsalbsig.swarch.justintime.entity.Employee;
import edu.hsalbsig.swarch.justintime.exception.EntityNotFoundException;
import edu.hsalbsig.swarch.justintime.exception.InvalidCredentialsException;

@RequestScoped
@Stateful
public class EmployeeService {
    private static final int DEFAULT_ROLE_ID = 1;
    
	@EJB
	private EmployeeDao employeeDao;
	
	@EJB
	private UserRoleDao userRoleDao;

	@Inject
	private ModelMapper mapper;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EmployeeDto createEmployee(final EmployeeRegistrationDto dto) throws Exception {
		var employee = mapper.map(dto, Employee.class);

		employee.setPasswordHash(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
		
		try {
		    employee.setUserRole(userRoleDao.findById(DEFAULT_ROLE_ID));
		} catch (EntityNotFoundException ex) {
		    throw new Exception();
		}

		employeeDao.createEmployee(employee);

		return mapper.map(employee, EmployeeExpandedDto.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Collection<EmployeeDto> getAllEmployees(final boolean expandRole) {		
		var employeeStream = employeeDao.getAllEmployees().stream();
		
		Stream<EmployeeDto> dtoStream;
		
		if (expandRole) {
		    dtoStream = employeeStream.map(x -> mapper.map(x, EmployeeExpandedDto.class));
		} else {
		    dtoStream = employeeStream.map(x -> mapper.map(x, EmployeeSimpleDto.class));
		}
		
		return dtoStream.collect(Collectors.toList());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EmployeeDto getEmployeeById(final int employeeId) throws EntityNotFoundException {
		var employee = employeeDao.findById(employeeId);
		return mapper.map(employee, EmployeeSimpleDto.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EmployeeDto getEmployeeByIdExpanded(final int employeeId) throws EntityNotFoundException {
		var employee = employeeDao.findById(employeeId);
		return mapper.map(employee, EmployeeExpandedDto.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EmployeeDto updateEmployee(final int employeeId, final EmployeeDto employee) throws EntityNotFoundException {
		var currentState = employeeDao.findById(employeeId);

		currentState.setName(employee.getName());
		currentState.setEmail(employee.getEmail());
		currentState.setHoursPerDay(employee.getHoursPerDay());

		employeeDao.updateEmployee(currentState);

		return mapper.map(currentState, EmployeeSimpleDto.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteEmployee(final int employeeId) throws EntityNotFoundException {
		employeeDao.deleteEmployee(employeeId);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Collection<TimeBookingDto> getTimebookingsForEmployee(final int employeeId) throws EntityNotFoundException {
		var employee = employeeDao.findById(employeeId);

		return employee.getTimebookings().stream().map(x -> mapper.map(x, TimeBookingDto.class))
				.collect(Collectors.toList());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Collection<ProjectDto> getProjectsForEmployee(final int employeeId) throws EntityNotFoundException {
		var employee = employeeDao.findById(employeeId);

		return employee.getProjectMemberships().stream().map(x -> mapper.map(x.getProject(), ProjectDto.class))
				.collect(Collectors.toList());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EmployeeSimpleDto checkEmployeeCredentials(final CredentialsDto credentials)
			throws InvalidCredentialsException {
		try {
			var employee = employeeDao.getEmployeeByEmail(credentials.getEmail());

			if (!BCrypt.checkpw(credentials.getPassword(), employee.getPasswordHash())) {
				throw new InvalidCredentialsException();
			}

			return mapper.map(employee, EmployeeSimpleDto.class);
		} catch (EntityNotFoundException ex) {
			throw new InvalidCredentialsException();
		}
	}
}
