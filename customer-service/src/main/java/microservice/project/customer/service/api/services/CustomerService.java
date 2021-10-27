package microservice.project.customer.service.api.services;

import java.util.List;

import microservice.project.customer.service.api.entities.Customer;

public interface CustomerService {
	List<Customer> findAll();
	Customer findOne(Long id);
	Customer save(Customer customer);
	void delete(Long id);
}
