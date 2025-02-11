package it.rad.elearning_platform.controllers;

import it.rad.elearning_platform.model.Customer;
import it.rad.elearning_platform.service.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {
    CustomerService customerService;
    Customer customer;

    @PostMapping("/customer")
    public Customer addCustomer(String firstName, String lastName, String phoneNumber,
                                String vatNumber, String company, List<String> emails){
        customer = new Customer(firstName, lastName, phoneNumber, vatNumber, company, emails);
        customer = customerService.saveCustomer(customer);
        return customer;
    }

    @PostMapping("/allCustomers")
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomer();
    }
}
