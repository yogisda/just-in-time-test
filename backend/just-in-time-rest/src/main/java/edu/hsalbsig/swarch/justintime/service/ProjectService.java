package edu.hsalbsig.swarch.justintime.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.modelmapper.ModelMapper;

import edu.hsalbsig.swarch.justintime.configuration.UserPrincipalBean;
import edu.hsalbsig.swarch.justintime.dao.CustomerDao;
import edu.hsalbsig.swarch.justintime.dao.EmployeeDao;
import edu.hsalbsig.swarch.justintime.dao.ProjectDao;
import edu.hsalbsig.swarch.justintime.dao.ProjectMembershipDao;
import edu.hsalbsig.swarch.justintime.dao.ProjectRoleDao;
import edu.hsalbsig.swarch.justintime.dao.UserRoleDao;
import edu.hsalbsig.swarch.justintime.dto.DataValidationException;
import edu.hsalbsig.swarch.justintime.dto.EmployeeDto;
import edu.hsalbsig.swarch.justintime.dto.ProjectDto;
import edu.hsalbsig.swarch.justintime.dto.ProjectMembershipDto;
import edu.hsalbsig.swarch.justintime.dto.RoleDto;
import edu.hsalbsig.swarch.justintime.entity.Employee;
import edu.hsalbsig.swarch.justintime.entity.Project;
import edu.hsalbsig.swarch.justintime.entity.ProjectMembership;
import edu.hsalbsig.swarch.justintime.entity.ProjectMembershipId;
import edu.hsalbsig.swarch.justintime.entity.ProjectRole;
import edu.hsalbsig.swarch.justintime.exception.EntityNotFoundException;

@RequestScoped
@Stateful
public class ProjectService {
	@EJB
	private ProjectDao projectDao;
	@EJB
	private ProjectMembershipDao projectMembershipDao;
	@EJB
	private EmployeeDao employeeDao;
	@EJB
	private CustomerDao customerDao;
	@EJB
	private ProjectRoleDao projectRoleDao;
	@EJB
	private UserRoleDao userRoleDao;

	@Inject
	private UserPrincipalBean userPrincipal;

	@Inject
	private ModelMapper mapper;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ProjectDto findById(int id) throws EntityNotFoundException {
		return mapper.map(projectDao.findById(id), ProjectDto.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Collection<ProjectDto> getAllProjects() {
		return projectDao.getAll().stream().map(x -> mapper.map(x, ProjectDto.class)).collect(Collectors.toList());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ProjectDto create(ProjectDto projectDto) throws DataValidationException, EntityNotFoundException {
		Project project = mapper.map(projectDto, Project.class);

		try {
			project.setCustomer(customerDao.findById(projectDto.getCustomer()));

			projectDao.create(project);

			ProjectMembership prjMem = new ProjectMembership();
			prjMem.setEmployee(employeeDao.findById(userPrincipal.getId()));
			prjMem.setProject(projectDao.findById(project.getProjectId()));
			prjMem.setRole(projectRoleDao.findById(1));
			prjMem.setCapacity(1);

			projectMembershipDao.create(prjMem);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataValidationException();
		}

		return mapper.map(project, ProjectDto.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ProjectDto update(ProjectDto projectDto) throws DataValidationException {
		Project project = mapper.map(projectDto, Project.class);

		try {
			project.setCustomer(customerDao.findById(projectDto.getCustomer()));
			projectDao.findById(project.getProjectId());
			projectDao.update(project);
		} catch (Exception e) {
			throw new DataValidationException();
		}

		return mapper.map(project, ProjectDto.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(int id) throws EntityNotFoundException {
		projectDao.delete(id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Collection<ProjectMembershipDto> getProjectMemberships(Integer projectId) throws EntityNotFoundException {
		Project project = projectDao.findById(projectId);
		Collection<ProjectMembership> projectMemberships = project.getProjectMembership();
		Collection<ProjectMembershipDto> projectMembershipDtos = new ArrayList<>();

		for (ProjectMembership p : projectMemberships) {
			Employee employee = employeeDao.findById(p.getProjectMembershipId().getEmployeeId());
			ProjectMembershipDto temp = new ProjectMembershipDto(p.getProjectMembershipId().getProjectId(),
					mapper.map(employee, EmployeeDto.class), p.getCapacity(), mapper.map(p.getRole(), RoleDto.class));
			projectMembershipDtos.add(temp);
		}

		return projectMembershipDtos;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Collection<EmployeeDto> getProjectMembers(Integer projectId) throws EntityNotFoundException {
		Project project = projectDao.findById(projectId);
		Collection<ProjectMembership> projectMemberships = project.getProjectMembership();
		Collection<EmployeeDto> employeeDtos = new ArrayList<>();

		for (ProjectMembership p : projectMemberships) {
			employeeDtos.add(mapper.map(p.getEmployee(), EmployeeDto.class));
		}

		return employeeDtos;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ProjectMembershipDto addMember(ProjectMembershipDto prjMemDto)
			throws DataValidationException, EntityNotFoundException {
		ProjectMembership prjMem = new ProjectMembership();
		Employee employee = employeeDao.findById(prjMemDto.getEmployee().getId());
		Project project = projectDao.findById(prjMemDto.getProject());
		ProjectRole role = projectRoleDao.findById(prjMemDto.getRole().getId());

		try {
			prjMem.setProject(project);
			prjMem.setEmployee(employee);
			prjMem.setCapacity(prjMemDto.getCapacity());
			prjMem.setRole(role);

			projectMembershipDao.create(prjMem);
		} catch (Exception e) {
			throw new DataValidationException();
		}

		return mapper.map(prjMem, ProjectMembershipDto.class);
	}

	public ProjectMembershipDto updateMember(ProjectMembershipDto prjMemDto) throws EntityNotFoundException {
		ProjectMembershipId prjMemId = new ProjectMembershipId(prjMemDto.getEmployee().getId(), prjMemDto.getProject());
		ProjectMembership prjMem = projectMembershipDao.findById(prjMemId);

		prjMem.setCapacity(prjMemDto.getCapacity());
		prjMem.setRole(projectRoleDao.findById(prjMemDto.getRole().getId()));

		projectMembershipDao.update(prjMem);

		return mapper.map(prjMem, ProjectMembershipDto.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteMember(Integer projectId, Integer employeeId) throws EntityNotFoundException {
		projectMembershipDao.delete(new ProjectMembershipId(employeeId, projectId));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean hasWriteAccess(int projectId) {
		var role = new ProjectRole();
		role.setRoleId(1);

		var currentUserId = userPrincipal.getId();

		return projectMembershipDao.getProjectMembersByRole(projectId, role).stream()
				.anyMatch(x -> x.getEmployee().getEmployeeId() == currentUserId);
	}
}
