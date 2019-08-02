package edu.hsalbsig.swarch.justintime.dao;

import java.util.Collection;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import edu.hsalbsig.swarch.justintime.entity.Employee;
import edu.hsalbsig.swarch.justintime.exception.EntityNotFoundException;

@RequestScoped
@Stateful
public class EmployeeDao {
	@PersistenceContext(unitName = "justin-time")
	private EntityManager entityManager;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Collection<Employee> getAllEmployees() {
		return entityManager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Employee findById(int id) throws EntityNotFoundException {
		var employee = entityManager.find(Employee.class, id);

		if (employee == null) {
			throw new EntityNotFoundException();
		}

		return employee;
	}

	public Employee getEmployeeByEmail(String email) throws EntityNotFoundException {
		try {
			return entityManager.createQuery("SELECT e FROM Employee e WHERE e.email = '" + email + "'", Employee.class)
					.getSingleResult();
		} catch (NoResultException ex) {
			throw new EntityNotFoundException();
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createEmployee(Employee employee) {
		entityManager.persist(employee);
		entityManager.flush();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateEmployee(Employee employee) {
		entityManager.merge(employee);
		entityManager.flush();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteEmployee(int id) throws EntityNotFoundException {
		entityManager.remove(findById(id));
	}
}
