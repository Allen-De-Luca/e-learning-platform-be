package it.rad.elearning_platform.service;

import it.rad.elearning_platform.model.Appointment;
import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.Customer;
import it.rad.elearning_platform.model.User;
import it.rad.elearning_platform.repository.ReminderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static it.rad.elearning_platform.constants.Query.*;

@Service
public class ReminderService implements ReminderRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private KeyHolder id;

    @Override
    public User saveUser(User user) {
        id = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    INSERT_USER_QUERY,
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setLong(3, user.getContact().getId());
            return ps;
        }, id);

        user.setId(Objects.requireNonNull(id.getKey()).longValue());
        return user;
    }

    @Override
    public User checkUser(String username, String password) {

        try{
            return jdbcTemplate.query(CHECK_USER_CREDENTIALS, (rs, rowNum) -> new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("user_pass;word"),
                    rs.getLong("contact_id")
                    ), username, password).stream().findFirst().get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Contact saveContact(Contact contact) {
        id = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    INSERT_CONTACT_QUERY,
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, contact.getFirstName());
            ps.setString(2, contact.getLastName());
            return ps;
        }, id);

        contact.setId(Objects.requireNonNull(id.getKey()).longValue());
        jdbcTemplate.batchUpdate(INSERT_CONTACT_EMAIL_QUERY,
                contact.getEmails(), contact.getEmails().size(), (ps, email) -> {
                    ps.setLong(1, contact.getId());
                    ps.setString(2, email);
                });
        return contact;
    }

    @Override
    public void addContactEmail(Long contactId, List<String> emails) {
        jdbcTemplate.batchUpdate(ADD_CONTACT_EMAIL_BY_CONTACT_ID, emails, emails.size(), (ps, email) ->{
            ps.setLong(1, contactId);
            ps.setString(2, email);
        });
    }

    @Override
    public void deleteContactEmail(Long contactId, List<String> emails) {
        jdbcTemplate.batchUpdate(DELETE_CONTACT_EMAIL_BY_CONTACT_ID, emails, emails.size(), (ps, email) ->{
            ps.setLong(1, contactId);
            ps.setString(2, email);
        });
    }

    @Override
    public List<Contact> getAllContacts() {
        return jdbcTemplate.query(SELECT_ALL_CONTACTS, (rs, rowNum) -> new Contact(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                Arrays.asList(rs.getString("emails").split(", "))
        ));
    }

    @Override
    public Customer saveCustomer(Customer customer, Long contactId) {
        id = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    INSERT_CUSTOMER_QUERY,
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getPhoneNumber());
            ps.setString(4, customer.getVatNumber());
            ps.setString(5, customer.getCompany());
            return ps;
        }, id);

        customer.setId(Objects.requireNonNull(id.getKey()).longValue());

        jdbcTemplate.batchUpdate(INSERT_CUSTOMER_EMAIL_QUERY,
                customer.getEmails(), customer.getEmails().size(), (ps, email) -> {
                    ps.setLong(1, customer.getId());
                    ps.setString(2, email);

                });

        jdbcTemplate.update(INSERT_CUSTOMER_TO_CONTACT, customer.getId(), contactId);
        return customer;
    }

    @Override
    public List<Customer> getAllCustomerByUserId(Long userId) {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMERS_BY_USER_ID, (rs, rowNum) -> new Customer(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("phone_number"),
                rs.getString("vat_number"),
                rs.getString("company"),
                Arrays.asList(rs.getString("emails").split(", "))
        ), userId);
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        jdbcTemplate.update(INSERT_APPOINTMENT_QUERY, appointment.getCustomerId(),
                appointment.getUserId(),
                appointment.getAppointmentDate(),
                appointment.getReminderDate());
        appointment.setId(Objects.requireNonNull(id.getKey()).longValue());
        return appointment;
    }

    @Override
    public List<Appointment> getAllAppointmentByCustomer(Long customerId) {
        return jdbcTemplate.query(SELECT_ALL_APPOINTMENT_BY_CUSTOMER_ID, (rs, rowNum) -> new Appointment(
                rs.getLong("appointment_id"),
                rs.getLong("customer_id"),
                rs.getLong("user_id"),
                rs.getDate("appointment_date").toLocalDate(),
                rs.getDate("reminder_date").toLocalDate()
        ) , customerId);
    }

    @Override
    public void deleteAppointmentById(Long appointmentId) {
        jdbcTemplate.update(DELETE_APPOINTMENT_BY_ID, appointmentId);
    }
}
