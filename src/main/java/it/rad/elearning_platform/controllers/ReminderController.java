package it.rad.elearning_platform.controllers;

import it.rad.elearning_platform.model.Appointment;
import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.Customer;
import it.rad.elearning_platform.model.User;
import it.rad.elearning_platform.req.AuthReq;
import it.rad.elearning_platform.req.NewAppointmentReq;
import it.rad.elearning_platform.req.RegisterCustomerReq;
import it.rad.elearning_platform.req.RegistrationReq;
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

    @GetMapping("/auth")
//  public boolean authentication(String username, String password){
    public List<User> authentication(AuthReq authReq){
        return reminderService.checkUser(authReq.getUsername(), authReq.getPassword());
    }

    @GetMapping("/allContacts")
    public List<Contact> getAllContacts(){
        return reminderService.getAllContacts();
    }

    @PostMapping("/addCustomer/{contactId}")
//  public void addCustomer(String firstName, String lastName, String phoneNumber,
//  String vatNumber, String company, List<String> emails){
    public void addCustomer(@RequestBody RegisterCustomerReq registerCustomerReq,
                            @PathVariable Long contactId){
        customer = new Customer(registerCustomerReq.getFirstName(),
                registerCustomerReq.getLastName(), registerCustomerReq.getPhoneNumber(),
                registerCustomerReq.getVatNumber(), registerCustomerReq.getCompany(),
                registerCustomerReq.getEmail());
        customer = reminderService.saveCustomer(customer, contactId);
    }

    @GetMapping("/allCustomersByContact/{userId}")
    public List<Customer> getAllCustomersByUserId(@PathVariable Long userId){

        return reminderService.getAllCustomerByUserId(userId);
    }

    @PostMapping("/addAppointment")
//  public void saveAppointment(int customer_id, int user_id, LocalDate appointmentDate, int days)
    public void saveAppointment(NewAppointmentReq newAppointmentReq){
        appointment = new Appointment(newAppointmentReq.getCustomerId(),
                newAppointmentReq.getUserId(), newAppointmentReq.getAppointmentDate(),
                newAppointmentReq.getReminderDays());
        appointment = reminderService.saveAppointment(appointment);
    }

    @GetMapping("/allAppointmentByCustomerId/{customerId}")
    public List<Appointment> getAllAppointmentByCustomerId(@PathVariable Long customerId){
        return reminderService.getAllAppointemntByCustomer(customerId);
    }
}
