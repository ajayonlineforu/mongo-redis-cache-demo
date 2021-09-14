package com.example.redisdemo.web;

import com.example.redisdemo.model.CustomerDTO;
import com.example.redisdemo.persistance.entity.Customer;
import com.example.redisdemo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @GetMapping("/customers/lastname/{lastName}")
    public List<CustomerDTO> findCustomersByLastName(@PathVariable String lastName){
        return service.getCustomersByLastName(lastName);
    }

    @GetMapping("/customers/id/{id}")
    public CustomerDTO findCustomersByLastName(@PathVariable Integer id){
        return service.getCustomersById(id);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> findAllCustomers(){
        return new ResponseEntity<>(service.findAllCustomers(), HttpStatus.OK);
    }

    @PostMapping("/customers")
    public ResponseEntity saveOrUpdateCustomer(@RequestBody CustomerDTO customerDTO){
        service.saveOrUpdateCustomer(customerDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/customers/{customerID}")
    public ResponseEntity deleteCustomer(@PathVariable int customerID){
        service.deleteCustomer(customerID);
        return new ResponseEntity(HttpStatus.OK);
    }

}
