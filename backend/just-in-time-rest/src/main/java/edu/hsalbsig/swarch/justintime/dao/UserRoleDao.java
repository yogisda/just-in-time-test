package edu.hsalbsig.swarch.justintime.dao;

import java.util.Collection;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.hsalbsig.swarch.justintime.dto.DataValidationException;
import edu.hsalbsig.swarch.justintime.entity.UserRole;
import edu.hsalbsig.swarch.justintime.exception.EntityNotFoundException;

@RequestScoped
@Stateful
public class UserRoleDao {
    @PersistenceContext(unitName = "justin-time")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Collection<UserRole> getAll() {
        return em.createQuery("SELECT u FROM UserRole u", UserRole.class).getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UserRole findById(int id) throws EntityNotFoundException {
        UserRole userRole = em.find(UserRole.class, id);

        if (userRole == null) {
            throw new EntityNotFoundException();
        }

        return userRole;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void create(UserRole userRole) throws DataValidationException {
        try {
            em.persist(userRole);
            em.flush();
        } catch (Exception e) {
            throw new DataValidationException();
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void update(UserRole userRole) {
        em.merge(userRole);
        em.flush();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(int id) throws EntityNotFoundException {
        em.remove(findById(id));
    }
    
}
