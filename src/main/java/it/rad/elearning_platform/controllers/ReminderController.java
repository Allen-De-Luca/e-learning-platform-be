package it.rad.elearning_platform.controllers;

import it.rad.elearning_platform.model.Appointment;
import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.Customer;
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

    @GetMapping("/allContacts")
    public List<Contact> getAllContacts(){
        return reminderService.getAllContacts();
    }

    @PostMapping("/addCustomer/{contactId}")
    public void addCustomer(@RequestBody NewCustomerReq newCustomerReq,
                            @PathVariable Long contactId){
        reminderService.saveCustomer(newCustomerReq.getFirstName(),
                newCustomerReq.getLastName(), newCustomerReq.getPhoneNumber(),
                newCustomerReq.getVatNumber(), newCustomerReq.getCompany(),
                newCustomerReq.getEmail(), contactId);
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
        return reminderService.getAllCustomerByContactId(userId);
    }

    @GetMapping("/addAppointment")
    public Long saveAppointment(@RequestBody AppointmentReq appointmentReq){
        return reminderService.saveAppointment(appointmentReq.getAppId(),appointmentReq.getCustomerId(),
                appointmentReq.getContactId(),
                appointmentReq.getAppointmentDate(),
                appointmentReq.getReminderDate(), appointmentReq.getNotes());
    }

    @GetMapping("/allAppointmentByCustomerId/{customerId}")
    public List<Appointment> getAllAppointmentByCustomerId(@PathVariable Long customerId){
        return reminderService.getAllAppointmentByCustomer(customerId);
    }

    @DeleteMapping("/deleteAppointment/{appointmentId}")
    public void deleteAppointment(@PathVariable Long appointmentId){
        reminderService.deleteAppointmentById(appointmentId);
    }

    @PostMapping("/addAppointmentNote")
    public void addAppointmentNote(@RequestBody NewNoteReq newNoteReq){
        reminderService.saveAppointmentNote(newNoteReq.getAppointmentId(), newNoteReq.getNote());
    }

    @GetMapping("/getEvents/{userId}")
    public EventListRsp getEventsByUserId(@PathVariable Long userId){
        return reminderService.getEventsByContactId(userId);
    }
}
