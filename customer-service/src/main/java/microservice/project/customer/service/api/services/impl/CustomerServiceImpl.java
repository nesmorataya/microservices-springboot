package microservice.project.customer.service.api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import microservice.project.customer.service.api.dao.CustomerDao;
import microservice.project.customer.service.api.entities.Customer;
import microservice.project.customer.service.api.services.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDao customerDao;

	@Override
	public List<Customer> findAll() {
		return (List<Customer>) customerDao.findAll();
	}

	@Override
	public Customer findOne(Long id) {
		return customerDao.findById(id).orElse(null);
	}

	@Override
	public Customer save(Customer customer) {
		return customerDao.save(customer);
	}

	@Override
	public void delete(Long id) {
		customerDao.deleteById(id);
	}

}
