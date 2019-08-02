package edu.hsalbsig.swarch.justintime.dao;

import java.util.Collection;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.hsalbsig.swarch.justintime.entity.ProjectRole;
import edu.hsalbsig.swarch.justintime.exception.EntityNotFoundException;

@RequestScoped
@Stateful
public class ProjectRoleDao {
	@PersistenceContext(unitName = "justin-time")
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<ProjectRole> getAll() {
		return em.createQuery("SELECT p FROM ProjectRole p", ProjectRole.class).getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ProjectRole findById(int id) throws EntityNotFoundException {
		ProjectRole projectRole = em.find(ProjectRole.class, id);

		if (projectRole == null) {
			throw new EntityNotFoundException();
		}

		return projectRole;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void create(ProjectRole projectRole) {
		em.persist(projectRole);
		em.flush();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void update(ProjectRole projectRole) {
		em.merge(projectRole);
		em.flush();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(int id) throws EntityNotFoundException {
		em.remove(findById(id));
	}
}
