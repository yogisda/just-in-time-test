package edu.hsalbsig.swarch.justintime.dao;

import java.util.Collection;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.hsalbsig.swarch.justintime.dto.DataValidationException;
import edu.hsalbsig.swarch.justintime.entity.Milestone;
import edu.hsalbsig.swarch.justintime.exception.EntityNotFoundException;

@RequestScoped
@Stateful
public class MilestoneDao {
	@PersistenceContext(unitName = "justin-time")
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<Milestone> getAll() {
		return em.createQuery("SELECT m FROM Milestone m", Milestone.class).getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Milestone findById(int id) throws EntityNotFoundException {
		Milestone milestone = em.find(Milestone.class, id);

		if (milestone == null) {
			throw new EntityNotFoundException();
		}

		return milestone;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void create(Milestone milestone) throws DataValidationException {
		try {
			em.persist(milestone);
			em.flush();
		} catch (Exception e) {
			throw new DataValidationException();
		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void update(Milestone milestone) throws DataValidationException {
		try {
			findById(milestone.getMilestoneId());
			em.merge(milestone);
			em.flush();
		} catch (Exception e) {
			throw new DataValidationException();
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(int id) throws EntityNotFoundException {
		em.remove(findById(id));
	}
}
