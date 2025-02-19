package it.rad.elearning_platform.controllers;

import it.rad.elearning_platform.model.Appointment;
import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.Customer;
import it.rad.elearning_platform.model.User;
import it.rad.elearning_platform.req.*;
import it.rad.elearning_platform.responseBody.EventListRsp;
import it.rad.elearning_platform.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReminderController {

    @Autowired
    ReminderService reminderService;
    User user;
    Contact contact;
    Customer customer;
    Appointment appointment;

    @PostMapping("/registration")
    public void addUser(@RequestBody RegistrationReq registrationReq){
        contact = new Contact(registrationReq.getFirstName(),
                registrationReq.getLastName(),
                registrationReq.getEmail());
        contact = reminderService.saveContact(contact);
        user = new User(registrationReq.getUsername(), registrationReq.getPassword(), contact);
        user = reminderService.saveUser(user);
    }

    @PostMapping("/auth")
    public User authentication(@RequestBody AuthReq authReq){
        return reminderService.checkUser(authReq.getUsername(), authReq.getPassword());
    }

    @GetMapping("/allContacts")
    public List<Contact> getAllContacts(){
        return reminderService.getAllContacts();
    }

    @PostMapping("/addCustomer/{contactId}")
    public void addCustomer(@RequestBody RegisterCustomerReq registerCustomerReq,
                            @PathVariable Long contactId){
        customer = new Customer(registerCustomerReq.getFirstName(),
                registerCustomerReq.getLastName(), registerCustomerReq.getPhoneNumber(),
                registerCustomerReq.getVatNumber(), registerCustomerReq.getCompany(),
                registerCustomerReq.getEmail());
        customer = reminderService.saveCustomer(customer, contactId);
    }

    @PostMapping("/addContactEmail")
    public void addContactEmail(@RequestBody ContactEmailReq contactEmailReq){
        reminderService.addContactEmail(contactEmailReq.getContactId(), contactEmailReq.getEmail());
    }

    @DeleteMapping("/deleteContactEmail")
    public void deleteContactEmail(@RequestBody ContactEmailReq contactEmailReq){
        reminderService.deleteContactEmail(contactEmailReq.getContactId(), contactEmailReq.getEmail());
    }

    @GetMapping("/allCustomersByContact/{userId}")
    public List<Customer> getAllCustomersByUserId(@PathVariable Long userId){
        return reminderService.getAllCustomerByUserId(userId);
    }

    @PostMapping("/addAppointment")
    public void saveAppointment(@RequestBody NewAppointmentReq newAppointmentReq){
        appointment = new Appointment(newAppointmentReq.getCustomerId(),
                newAppointmentReq.getUserId(), newAppointmentReq.getAppointmentDate(),
                newAppointmentReq.getReminderDays());
        appointment = reminderService.saveAppointment(appointment);
    }

    @GetMapping("/allAppointmentByCustomerId/{customerId}")
    public List<Appointment> getAllAppointmentByCustomerId(@PathVariable Long customerId){
        return reminderService.getAllAppointmentByCustomer(customerId);
    }

    @DeleteMapping("/deleteAppointment/{appointmentId}")
    public void deleteAppointment(@PathVariable Long appointmentId){
        reminderService.deleteAppointmentById(appointmentId);
    }

    @GetMapping("/getEvent/{userId}")
    public EventListRsp getEventByUserId(@PathVariable Long userId){
        return reminderService.getEventByUserId(userId);
    }
}
