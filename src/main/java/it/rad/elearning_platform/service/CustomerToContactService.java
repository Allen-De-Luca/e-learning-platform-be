package it.rad.elearning_platform.service;

import it.rad.elearning_platform.model.CustomerToContact;
import it.rad.elearning_platform.repository.CustomerToContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerToContactService {

    @Autowired
    private CustomerToContactRepository customerToContactRepository;

    public List<CustomerToContact> getAllCustomerToContact() {
        return customerToContactRepository.findAll();
    }

    public CustomerToContact getCustomerToContactById(Long id) {
        return customerToContactRepository.findById(id).orElse(null);
    }

    public CustomerToContact saveCustomerToContact(CustomerToContact customerToContact) {
        return customerToContactRepository.save(customerToContact);
    }

    public void deleteCustomerToContact(Long id) {
        customerToContactRepository.deleteById(id);
    }
}