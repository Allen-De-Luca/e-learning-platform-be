package it.rad.elearning_platform.repository;

import it.rad.elearning_platform.model.Appointment;
import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.Customer;
import it.rad.elearning_platform.model.User;

import java.util.List;

public interface ReminderRepo {

    User saveUser(User User);

    List<User> checkUser(String username, String password);

    Contact saveContact(Contact contact);

    List<Contact> getAllContacts();

    Customer saveCustomer(Customer customer, Long contactId);

    List<Customer> getAllCustomerByUserId(Long userId);

    Appointment saveAppointment(Appointment appointment);

    List<Appointment> getAllAppointemntByCustomer(Long customerId);
}
