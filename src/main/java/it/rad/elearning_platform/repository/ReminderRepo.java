package it.rad.elearning_platform.repository;

import it.rad.elearning_platform.model.Appointment;
import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.Customer;
import it.rad.elearning_platform.rsp.EventListRsp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepo {

    void addContactEmail(Long contactId, List<String> emails);

    void deleteContactEmail(Long contactId, List<String> emails);

    List<Contact> getAllContacts();

    void saveCustomer(String firstName, String lastName, String phoneNumber, String vatNumber, String company, List<String> emails, Long contactId, Long customerId);

    void addCustomerEmail(Long customerId, List<String> emails);

    void deleteCustomerEmail(Long customerId, List<String> emails);

    Long findCustomerToContactId(Long customerId, Long contactId);

    List<Customer> getAllCustomerByContactId(Long contactId);

    Long saveAppointment(Long appointmentId, Long customerId, Long contactId, LocalDateTime appointmentDate, LocalDate reminderDate, String notes);

    List<Appointment> getAllAppointmentByCustomer(Long customerId);

    void saveAppointmentNote(Long appointmentId, String note);

    EventListRsp getEventsByContactId(Long contactId);

    void deleteAppointmentById(Long appointmentId);

}
