package microservice.project.customer.service.api.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import microservice.project.customer.service.api.entities.Customer;

@Service
public interface CustomerDao extends CrudRepository<Customer, Long> {

}
