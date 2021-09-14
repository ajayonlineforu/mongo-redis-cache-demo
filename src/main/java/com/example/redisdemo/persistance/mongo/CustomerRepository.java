package com.example.redisdemo.persistance.mongo;

import com.example.redisdemo.persistance.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {

	public List<Customer> findByFirstName(String firstName);
	public List<Customer> findByLastName(String lastName);
	public Customer findByCustomerId(int id);

}
