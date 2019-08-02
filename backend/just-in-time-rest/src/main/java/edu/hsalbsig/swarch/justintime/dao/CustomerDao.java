package edu.hsalbsig.swarch.justintime.dao;

import java.util.Collection;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.hsalbsig.swarch.justintime.dto.DataValidationException;
import edu.hsalbsig.swarch.justintime.entity.Customer;
import edu.hsalbsig.swarch.justintime.exception.EntityNotFoundException;

@RequestScoped
@Stateful
public class CustomerDao {
	@PersistenceContext(unitName = "justin-time")
	private EntityManager entityManager;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Customer findById(int id) throws EntityNotFoundException {
		Customer customer = entityManager.find(Customer.class, id);

		if (customer == null) {
			throw new EntityNotFoundException();
		}

		return customer;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void create(Customer customer) throws DataValidationException {
		try {
		    entityManager.persist(customer);
		    entityManager.flush();
        } catch (Exception e) {
            throw new DataValidationException();
        }
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void update(Customer customer) throws DataValidationException {
		try {
		    entityManager.merge(customer);
		    entityManager.flush();
        } catch (Exception e) {
            throw new DataValidationException();
        }
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(int id) throws EntityNotFoundException {
		var customer = findById(id);
		
		if (customer == null) {
            throw new EntityNotFoundException();
        }

		entityManager.remove(customer);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Collection<Customer> getAllCustomer() {
		return entityManager.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
	}

}
