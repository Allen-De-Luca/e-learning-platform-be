package it.rad.elearning_platform.repository;

import it.rad.elearning_platform.model.Customer;

import java.util.List;

public interface CustomerRepository {

    Customer saveCustomer(Customer customer);

    List<Customer> getAllCustomer();
}
