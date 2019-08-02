package edu.hsalbsig.swarch.justintime.dao;

import java.util.Collection;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import edu.hsalbsig.swarch.justintime.entity.ProjectMembership;
import edu.hsalbsig.swarch.justintime.entity.ProjectMembershipId;
import edu.hsalbsig.swarch.justintime.entity.ProjectRole;
import edu.hsalbsig.swarch.justintime.exception.EntityNotFoundException;

@RequestScoped
@Stateful
public class ProjectMembershipDao {
	@PersistenceContext(unitName = "justin-time")
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Collection<ProjectMembership> getAll() {
		return em.createQuery("SELECT p FROM ProjectMembership p", ProjectMembership.class).getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ProjectMembership findById(ProjectMembershipId id) throws EntityNotFoundException {
		ProjectMembership projectMembership = em.find(ProjectMembership.class, id);

		if (projectMembership == null) {
			throw new EntityNotFoundException();
		}

		return projectMembership;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void create(ProjectMembership projectMembership) {
		em.persist(projectMembership);
		em.flush();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void update(ProjectMembership projectMembership) {
		em.merge(projectMembership);
		em.flush();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(ProjectMembershipId id) throws EntityNotFoundException {
		em.remove(findById(id));
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Collection<ProjectMembership> getProjectMembersByRole(int projectId, ProjectRole role) {
	    TypedQuery<ProjectMembership> query;
	    
	    if (role == null) {
	        query = em.createQuery("SELECT m FROM ProjectMembership m WHERE m.project.projectId = " + Integer.toString(projectId), 
	                ProjectMembership.class);
	    } else {
	        query = em.createQuery("SELECT m FROM ProjectMembership m WHERE m.project.projectId = " + Integer.toString(projectId)
	                + " AND m.role.roleId = " + role.getRoleId().toString(), ProjectMembership.class);
	    }
	    
	    return query.getResultList();
	}
}
