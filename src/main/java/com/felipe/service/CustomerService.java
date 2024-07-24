package com.felipe.service;

import com.felipe.model.entity.Customer;
import com.felipe.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    public Customer registerCustomer(Customer customer) {
        return repository.save(customer);
    }
}
