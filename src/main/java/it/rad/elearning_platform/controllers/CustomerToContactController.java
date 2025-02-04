package it.rad.elearning_platform.controllers;

import it.rad.elearning_platform.model.CustomerToContact;
import it.rad.elearning_platform.service.CustomerToContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customerToContact")
public class CustomerToContactController {

    @Autowired
    private CustomerToContactService customerToContactService;

    @GetMapping
    public List<CustomerToContact> getAllCustomerToContact() {
        return customerToContactService.getAllCustomerToContact();
    }

    @GetMapping("/{id}")
    public CustomerToContact getCustomerToContactById(@PathVariable Long id) {
        return customerToContactService.getCustomerToContactById(id);
    }

    @PostMapping
    public CustomerToContact createCustomerToContact(@RequestBody CustomerToContact customerToContact) {
        return customerToContactService.saveCustomerToContact(customerToContact);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerToContact(@PathVariable Long id) {
        customerToContactService.deleteCustomerToContact(id);
    }
}
