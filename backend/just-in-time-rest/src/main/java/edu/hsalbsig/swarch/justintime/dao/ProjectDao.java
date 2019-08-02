package edu.hsalbsig.swarch.justintime.dao;

import java.util.Collection;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.hsalbsig.swarch.justintime.entity.Project;
import edu.hsalbsig.swarch.justintime.exception.EntityNotFoundException;

@RequestScoped
@Stateful
public class ProjectDao {
	@PersistenceContext(unitName = "justin-time")
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<Project> getAll() {
		return em.createQuery("SELECT p FROM Project p", Project.class).getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Project findById(int id) throws EntityNotFoundException {
		Project project = em.find(Project.class, id);

		if (project == null) {
			throw new EntityNotFoundException();
		}

		return project;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void create(Project project) {
		em.persist(project);
		em.flush();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void update(Project project) {
		em.merge(project);
		em.flush();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Integer id) throws EntityNotFoundException {
		em.remove(findById(id));
	}
}
