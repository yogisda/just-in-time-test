package edu.hsalbsig.swarch.justintime.service;

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
import edu.hsalbsig.swarch.justintime.dao.MilestoneDao;
import edu.hsalbsig.swarch.justintime.dao.ProjectDao;
import edu.hsalbsig.swarch.justintime.dao.ProjectMembershipDao;
import edu.hsalbsig.swarch.justintime.dto.DataValidationException;
import edu.hsalbsig.swarch.justintime.dto.MilestoneDto;
import edu.hsalbsig.swarch.justintime.entity.Milestone;
import edu.hsalbsig.swarch.justintime.entity.Project;
import edu.hsalbsig.swarch.justintime.entity.ProjectRole;
import edu.hsalbsig.swarch.justintime.exception.EntityNotFoundException;

@RequestScoped
@Stateful
public class MilestoneService {
	@EJB
	private MilestoneDao milestoneDao;
	@EJB
	private ProjectDao projectDao;
	@EJB
	private ProjectMembershipDao projectMembershipDao;

	@Inject
	private ModelMapper mapper;
	@Inject
	private UserPrincipalBean userPrincipal;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MilestoneDto findById(int id) throws EntityNotFoundException {
		return mapper.map(milestoneDao.findById(id), MilestoneDto.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Collection<MilestoneDto> getAllMileStones() {
		return milestoneDao.getAll().stream().map(x -> mapper.map(x, MilestoneDto.class)).collect(Collectors.toList());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Collection<MilestoneDto> getMileStonesOfProject(Integer projectId) throws EntityNotFoundException {
		Project project = projectDao.findById(projectId);
		return project.getMilestones().stream().map(x -> mapper.map(x, MilestoneDto.class))
				.collect(Collectors.toList());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MilestoneDto create(MilestoneDto milestoneDto) throws DataValidationException {
		Milestone milestone = new Milestone();
		try {
			milestone.setProject(projectDao.findById(milestoneDto.getProject()));
		} catch (Exception e) {
			throw new DataValidationException();
		}

		milestone.setName(milestoneDto.getName());
		milestone.setDescription(milestoneDto.getDescription());
		milestone.setEstimatedHours(milestoneDto.getEstimatedHours());
		milestone.setEndDate(milestoneDto.getEndDate());

		milestoneDao.create(milestone);

		return mapper.map(milestone, MilestoneDto.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MilestoneDto update(MilestoneDto milestoneDto) throws DataValidationException, EntityNotFoundException {
		Milestone milestone = milestoneDao.findById(milestoneDto.getId());

		milestone.setName(milestoneDto.getName());
		milestone.setDescription(milestoneDto.getDescription());
		milestone.setEstimatedHours(milestoneDto.getEstimatedHours());
		milestone.setEndDate(milestoneDto.getEndDate());

		milestoneDao.update(milestone);

		return mapper.map(milestone, MilestoneDto.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(int id) throws EntityNotFoundException {
		milestoneDao.delete(id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean hasWriteAccess(int milestoneId) throws EntityNotFoundException {
		var role = new ProjectRole();
		var projectId = milestoneDao.findById(milestoneId).getProject().getProjectId();
		var currentUserId = userPrincipal.getId();

		role.setRoleId(1);

		return projectMembershipDao.getProjectMembersByRole(projectId, role).stream()
				.anyMatch(x -> x.getEmployee().getEmployeeId() == currentUserId);
	}
}
