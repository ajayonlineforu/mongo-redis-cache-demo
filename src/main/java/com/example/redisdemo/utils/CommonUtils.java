package com.example.redisdemo.utils;

import com.example.redisdemo.model.CustomerDTO;
import com.example.redisdemo.persistance.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

    public Customer getCustomerEntity(CustomerDTO dto){
        return Customer.builder()
                .customerId(dto.getCustomerId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .age(dto.getAge()).build();
    }

    public CustomerDTO getCustomerDTO(Customer customerEntity){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customerEntity.getCustomerId());
        customerDTO.setAge(customerEntity.getAge());
        customerDTO.setFirstName(customerEntity.getFirstName());
        customerDTO.setLastName(customerEntity.getLastName());
        return customerDTO;
    }
}
