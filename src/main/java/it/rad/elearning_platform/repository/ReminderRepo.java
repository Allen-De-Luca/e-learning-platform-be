package it.rad.elearning_platform.repository;

import it.rad.elearning_platform.model.Appointment;
import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.Customer;
import it.rad.elearning_platform.rsp.EventListRsp;

import java.util.List;

public interface ReminderRepo {

    void addContactUser(Contact contact, Long userId);

    void addContactEmail(Long contactId, List<String> emails);

    void deleteContactEmail(Long contactId, List<String> emails);

    List<Contact> getAllContacts();

    Customer saveCustomer(Customer customer, Long contactId);

    void addCustomerEmail(Long customerId, List<String> emails);

    void deleteCustomerEmail(Long customerId, List<String> emails);

    List<Customer> getAllCustomerByUserId(Long userId);

    Appointment saveAppointment(Appointment appointment);

    List<Appointment> getAllAppointmentByCustomer(Long customerId);

    EventListRsp getEventsByUserId(Long userId);

    void deleteAppointmentById(Long appointmentId);

}
