package it.rad.elearning_platform.repository;

import it.rad.elearning_platform.model.Appointment;
import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.Customer;
import it.rad.elearning_platform.rsp.EventListRsp;

import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepo {

    void addContactUser(Contact contact, Long userId);

    void addContactEmail(Long contactId, List<String> emails);

    void deleteContactEmail(Long contactId, List<String> emails);

    List<Contact> getAllContacts();

    void saveCustomer(String firstName, String lastName, String phoneNumber, String vatNumber, String company, List<String> emails, Long contactId);

    void addCustomerEmail(Long customerId, List<String> emails);

    void deleteCustomerEmail(Long customerId, List<String> emails);

    List<Customer> getAllCustomerByContactId(Long contactId);

    void saveAppointment(Long customerId, Long contactId, LocalDateTime appointmentDate, int days, String notes);

    List<Appointment> getAllAppointmentByCustomer(Long customerId);

    void saveAppointmentNote(Long appointmentId, String note);

    EventListRsp getEventsByContactId(Long contactId);

    void deleteAppointmentById(Long appointmentId);

}
