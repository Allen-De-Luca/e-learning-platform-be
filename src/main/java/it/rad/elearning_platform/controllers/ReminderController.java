package it.rad.elearning_platform.controllers;

import it.rad.elearning_platform.model.Appointment;
import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.Customer;
import it.rad.elearning_platform.model.User;
import it.rad.elearning_platform.req.*;
import it.rad.elearning_platform.rsp.EventListRsp;
import it.rad.elearning_platform.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "Authorization, Content-Type")
@RestController
@RequestMapping("/api")
public class ReminderController {

    @Autowired
    ReminderService reminderService;
    User user;
    Contact contact;
    Customer customer;
    Appointment appointment;

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
    public void addContactEmail(@RequestBody EmailReq emailReq){
        reminderService.addContactEmail(emailReq.getId(), emailReq.getEmail());
    }

    @DeleteMapping("/deleteContactEmail")
    public void deleteContactEmail(@RequestBody EmailReq emailReq){
        reminderService.deleteContactEmail(emailReq.getId(), emailReq.getEmail());
    }

    @PostMapping("/addCustomerEmail")
    public void addCustomerEmail(@RequestBody EmailReq emailReq){
        reminderService.addCustomerEmail(emailReq.getId(), emailReq.getEmail());
    }

    @DeleteMapping("/deleteCustomerEmail")
    public void deleteCustomerEmail(@RequestBody EmailReq emailReq){
        reminderService.deleteCustomerEmail(emailReq.getId(), emailReq.getEmail());
    }

    @GetMapping("/allCustomersByContact/{userId}")
    public List<Customer> getAllCustomersByUserId(@PathVariable Long userId){
        return reminderService.getAllCustomerByUserId(userId);
    }

    @PostMapping("/addAppointment")
    public void saveAppointment(@RequestBody NewAppointmentReq newAppointmentReq){
        appointment = new Appointment(newAppointmentReq.getCustomerId(),
                newAppointmentReq.getContactId(), newAppointmentReq.getAppointmentDate(),
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

    @GetMapping("/getEvents/{userId}")
    public EventListRsp getEventsByUserId(@PathVariable Long userId){
        return reminderService.getEventsByUserId(userId);
    }
}
