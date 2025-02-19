package it.rad.elearning_platform.repository;

import it.rad.elearning_platform.model.Appointment;
import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.Customer;
import it.rad.elearning_platform.model.User;
import it.rad.elearning_platform.responseBody.EventListRsp;

import java.util.List;

public interface ReminderRepo {

    User saveUser(User User);

    User checkUser(String username, String password);

    Contact saveContact(Contact contact);

    void addContactEmail(Long contactId, List<String> email);

    void deleteContactEmail(Long contactId, List<String> email);

    List<Contact> getAllContacts();

    Customer saveCustomer(Customer customer, Long contactId);

    List<Customer> getAllCustomerByUserId(Long userId);

    Appointment saveAppointment(Appointment appointment);

    List<Appointment> getAllAppointmentByCustomer(Long customerId);

    EventListRsp getEventByUserId(Long userId);

    void deleteAppointmentById(Long appointmentId);

}
