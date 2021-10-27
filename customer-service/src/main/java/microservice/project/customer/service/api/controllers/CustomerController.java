package microservice.project.customer.service.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import microservice.project.customer.service.api.entities.Customer;
import microservice.project.customer.service.api.services.CustomerService;

@RestController
@RequestMapping("customers")
public class CustomerController {

	private Logger log = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	@GetMapping
	public ResponseEntity<List<Customer>> findAll() {
		return ResponseEntity.ok(customerService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> findOne(@PathVariable Long id) {
		Customer customer = customerService.findOne(id);

		if (customer == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(customer);
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody Customer customer, BindingResult result) {
		Map<String, Object> response = new HashMap<String, Object>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("erros", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		Customer customerNew = customerService.save(customer);
		return new ResponseEntity<Customer>(customerNew, HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Customer customer, BindingResult result,
			@PathVariable(name = "id") Long id) {
		Customer customerDb = customerService.findOne(id);
		Customer customerUpdated = null;
		Map<String, Object> response = new HashMap<String, Object>();
		if (customerDb == null) {
			response.put("errors", "Customer not found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		customerDb.setName(customer.getName());
		customerDb.setLastname(customer.getLastname());

		customerUpdated = customerService.save(customerDb);

		return ResponseEntity.ok(customerUpdated);

	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			customerService.delete(id);
		} catch (DataAccessException e) {
			response.put("message", "Failed to delete client in database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("message", "Customer deleted successfully!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
	}

}
