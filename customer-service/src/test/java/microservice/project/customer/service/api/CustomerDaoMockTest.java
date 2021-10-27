package microservice.project.customer.service.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import microservice.project.customer.service.api.dao.CustomerDao;
import microservice.project.customer.service.api.entities.Customer;

@DataJpaTest
public class CustomerDaoMockTest {

	@Autowired
	private CustomerDao dao;
	
	@Test
	public void whenFindById_thenReturnACustomer() {
		Customer customer01 = new Customer();
		customer01.setId(1L);
		customer01.setName("Nestor");
		customer01.setLastname("Morataya");
		dao.save(customer01);
		
		Customer customer02 = (Customer) dao.findById(customer01.getId()).orElse(null);
		
		Assertions.assertThat(customer02).isNotNull();
		Assertions.assertThat(customer02.getId()).isEqualTo(customer01.getId());

	}
	
}
