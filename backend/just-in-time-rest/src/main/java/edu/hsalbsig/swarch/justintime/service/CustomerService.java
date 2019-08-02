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

import edu.hsalbsig.swarch.justintime.dao.CustomerDao;
import edu.hsalbsig.swarch.justintime.dto.CustomerDto;
import edu.hsalbsig.swarch.justintime.dto.DataValidationException;
import edu.hsalbsig.swarch.justintime.entity.Customer;
import edu.hsalbsig.swarch.justintime.exception.EntityNotFoundException;

@RequestScoped
@Stateful
public class CustomerService {

	@EJB
	private CustomerDao customerDao;
	
	@Inject
    private ModelMapper mapper;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public CustomerDto createCustomer(CustomerDto dto) throws EntityNotFoundException, DataValidationException {
        var customer = mapper.map(dto, Customer.class);
        customerDao.create(customer);
       
        return mapper.map(customer, CustomerDto.class);
    }
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Collection<CustomerDto> getAllCustomers() {
        return customerDao.getAllCustomer().stream()
                .map(x -> mapper.map(x, CustomerDto.class))
                .collect(Collectors.toList());
    }
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public CustomerDto getCustomerById(int id) throws EntityNotFoundException {
		return mapper.map(customerDao.findById(id), CustomerDto.class);
	}

	public CustomerDto updateCustomer(final int customerId, final CustomerDto customer) throws EntityNotFoundException, DataValidationException {
	    var currentState = customerDao.findById(customerId);
	    
	    currentState.setName(customer.getName());
	    currentState.setCity(customer.getCity());
	    currentState.setStreet(customer.getStreet());
	    currentState.setZipcode(customer.getZipcode());
	    
	    customerDao.update(currentState);
	    
	    return mapper.map(currentState, CustomerDto.class);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(int id) throws EntityNotFoundException {
		customerDao.delete(id);
	}
}
