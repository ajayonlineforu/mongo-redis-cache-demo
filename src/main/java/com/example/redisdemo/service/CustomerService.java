package com.example.redisdemo.service;

import com.example.redisdemo.model.CustomerDTO;
import com.example.redisdemo.persistance.entity.Customer;
import com.example.redisdemo.persistance.mongo.CustomerRepository;
import com.example.redisdemo.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository repository;
    private final CommonUtils commonUtils;

    @Cacheable(value = "customers-by-last-name", key = "#lastName", unless = "#result==null or #result.isEmpty()")
    public List<CustomerDTO> getCustomersByLastName(String lastName){
        log.info("Getting customer from DB for {}",lastName);
        List<Customer> customerList = repository.findByLastName(lastName);
        List<CustomerDTO> customers = customerList.stream()
                .peek(this::logCustomer)
                .map(commonUtils::getCustomerDTO).collect(Collectors.toList());
        return customers;
    }

    private void logCustomer(Customer customer) {
        try {
            log.info("Customer is :: {}", new ObjectMapper().writeValueAsString(customer));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Cacheable(value = "customer-by-id", key = "#id")
    public CustomerDTO getCustomersById(int id){
        log.info("Getting customer from DB for id {}",id);
        return commonUtils.getCustomerDTO(repository.findByCustomerId(id));
    }

    @Cacheable(value = "all-customers")
    public List<CustomerDTO> findAllCustomers(){
        log.info("Getting all customers ::");
        return repository.findAll().stream()
                .peek(this::logCustomer)
                .map(commonUtils::getCustomerDTO).collect(Collectors.toList());
    }

    @Caching(evict = {
            @CacheEvict(value = "all-customers", allEntries = true),
            @CacheEvict(value = "customers-by-last-name", key = "#customerDTO.lastName")
    })
    public void saveOrUpdateCustomer(CustomerDTO customerDTO) {
        Customer customer = commonUtils.getCustomerEntity(customerDTO);
        Customer existingCustomer = repository.findByCustomerId(customerDTO.getCustomerId());

        if(Objects.nonNull(existingCustomer)) {
            customer.setId(existingCustomer.getId());
            customer.setUpdatedAt(LocalDateTime.now());
        }else
        {
            customer.setCreatedAt(LocalDateTime.now());
        }

        repository.save(customer);
    }

    @Caching(evict = {
            @CacheEvict(value = "all-customers", allEntries = true),
            @CacheEvict(value = "customers-by-last-name", allEntries = true)
    })
    public void deleteCustomer(int customerId) {
        Customer customer = repository.findByCustomerId(customerId);
        if(!Objects.isNull(customer))
            repository.delete(customer);
    }
}
