package com.example.redisdemo.command;

import com.example.redisdemo.persistance.entity.Customer;
import com.example.redisdemo.persistance.mongo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class SaveToMongoCommand implements CommandLineRunner {

    private final CustomerRepository repository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Executing SaveToMongoCommand ..");
        repository.deleteAll();
        log.info("All records got deleted..");

         Customer cust1 = Customer.builder().customerId(1).firstName("ajay").lastName("kumar").age(40).build();
         cust1.setCreatedAt(LocalDateTime.now());
         saveCustomer(cust1);
         Customer cust2 = Customer.builder().customerId(2).firstName("abhay").lastName("kumar").age(35).build();
         cust2.setCreatedAt(LocalDateTime.now());
         saveCustomer(cust2);

        log.info("Records saved into Database..");
    }


    private void saveCustomer(Customer customer){
        repository.save(customer);
    }
}
