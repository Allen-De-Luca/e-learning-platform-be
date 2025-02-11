package it.rad.elearning_platform.service;

import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.Customer;
import it.rad.elearning_platform.repository.CustomerRepository;

import java.util.List;

public class CustomerService implements CustomerRepository {

    @Override
    public Customer saveCustomer(Customer customer) {
        
        return customer;
    }

    @Override
    public List<Customer> getAllCustomer() {
        return List.of();
    }
}
