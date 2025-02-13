package it.rad.elearning_platform.controllers;

import it.rad.elearning_platform.model.Appointment;
import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.Customer;
import it.rad.elearning_platform.model.User;
import it.rad.elearning_platform.req.AuthReq;
import it.rad.elearning_platform.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/login")
public class ReminderController {

    @Autowired
    ReminderService reminderService;
    User user;
    Contact contact;
    Customer customer;
    Appointment appointment;

    @PostMapping("/user")
    public void addUser(@RequestBody AuthReq authReq){
        contact = new Contact(authReq.getFirstName(), authReq.getLastName(), authReq.getEmail());
        contact = reminderService.saveContact(contact);
        user = new User(authReq.getUsername(), authReq.getPassword(), contact);
        user = reminderService.saveUser(user);
    }

    //@GetMapping("/login")
    public boolean authentication(String username, String password){
        return reminderService.checkUser(username, password);
    }

    @GetMapping("/allContacts")
    public List<Contact> getAllContacts(){
        return reminderService.getAllContacts();
    }

    @PostMapping("/customer")
    public void addCustomer(String firstName, String lastName, String phoneNumber,
                            String vatNumber, String company, List<String> emails){
        customer = new Customer(firstName, lastName, phoneNumber, vatNumber, company, emails);
        customer = reminderService.saveCustomer(customer);
    }

    @GetMapping("/allCustomersForUser")
    public List<Customer> getAllCustomers(User user){
        return reminderService.getAllCustomer();
    }

    @PostMapping("/addAppointment")
    public void saveAppointment(int customer_id, int user_id, LocalDate appointmentDate, int days){
        appointment = new Appointment(customer_id, user_id, appointmentDate, days);
        appointment = reminderService.saveAppointment(appointment);
    }
}
