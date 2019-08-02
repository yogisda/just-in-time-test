package edu.hsalbsig.swarch.justintime.dao;

import java.util.Collection;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.hsalbsig.swarch.justintime.entity.TimeBooking;
import edu.hsalbsig.swarch.justintime.exception.EntityNotFoundException;

@RequestScoped
@Stateful
public class TimeBookingDao {
	@PersistenceContext(unitName = "justin-time")
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<TimeBooking> getAll(int id) {
		return em.createQuery("SELECT t FROM TimeBooking t, Project p WHERE p.projectId=" + id, TimeBooking.class)
				.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public TimeBooking findById(int id) throws EntityNotFoundException {
		TimeBooking timeBooking = em.find(TimeBooking.class, id);

		if (timeBooking == null) {
			throw new EntityNotFoundException();
		}

		return timeBooking;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void create(TimeBooking timeBooking) {
		em.persist(timeBooking);
		em.flush();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void update(TimeBooking timeBooking) {
		em.merge(timeBooking);
		em.flush();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(int id) throws EntityNotFoundException {
		em.remove(findById(id));
	}
}
