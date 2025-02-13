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

@Service
public class ReminderService implements ReminderRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private KeyHolder id;

    private static final String INSERT_USER_QUERY =
            "INSERT INTO user(username, user_password, contact_id) values (?,?,?)";
    private static final String CHECK_USER_CREDENTIALS =
            "SELECT COUNT(*) FROM user WHERE username = ? AND user_password = ?";

    private static final String INSERT_CONTACT_QUERY =
            "INSERT INTO contact(first_name, last_name) values (?,?)";
    private static final String INSERT_CONTACT_EMAIL_QUERY =
            "INSERT INTO contact_email(contact_id, email) values (?,?)";
    private static final String SELECT_ALL_CONTACTS =
            "SELECT c.id, c.first_name, c.last_name, " +
                    "GROUP_CONCAT(ce.email SEPARATOR ', ') AS emails " +
                    "FROM contact c " +
                    "LEFT JOIN contact_email ce ON c.id = ce.contact_id " +
                    "GROUP BY c.id, c.first_name, c.last_name";

    private static final String INSERT_CUSTOMER_QUERY=
            "INSERT INTO customer (first_name, last_name, phone_number, vat_number, company) " +
                    "VALUES (?,?,?,?,?)";
    private static final String INSERT_CUSTOMER_EMAIL_QUERY=
            "INSERT INTO customer_email(customer_id, email) values (?,?)";
    private static final String SELECT_ALL_CUSTOMERS=
            "SELECT c.id, c.first_name, c.last_name, c.phone_number, c.vat_number, c.company" +
                    "GROUP_CONCAT(ce.email SEPARATOR ', ') AS emails " +
                    "FROM customer c " +
                    "LEFT JOIN customer_email ce ON c.id = ce.customer_id " +
                    "GROUP BY c.id, c.first_name, c.last_name, c.phone_number, c.vat_number, c.company";

    private static final String INSERT_APPOINTMENT_QUERY=
            "INSERT INTO appointment(customer_id, user_id, appointment_date, reminderDate)" +
                    "VALUES (?,?,?,?)";

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
            ps.setInt(3, user.getContact().getId());
            return ps;
        }, id);

        user.setId(Objects.requireNonNull(id.getKey()).intValue());
        return user;
    }

    @Override
    public Boolean checkUser(String username, String password) {
        Integer count = jdbcTemplate.queryForObject(CHECK_USER_CREDENTIALS,
                Integer.class, username, password);
        return count != null && count > 0;
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

        contact.setId(Objects.requireNonNull(id.getKey()).intValue());
        jdbcTemplate.batchUpdate(INSERT_CONTACT_EMAIL_QUERY,
                contact.getEmails(), contact.getEmails().size(), (ps, email) -> {
                    ps.setInt(1, contact.getId());
                    ps.setString(2, email);

                });
        return contact;
    }

    @Override
    public List<Contact> getAllContacts() {
        return jdbcTemplate.query(SELECT_ALL_CONTACTS, (rs, rowNum) -> new Contact(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                Arrays.asList(rs.getString("emails").split(", "))
        ));
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        jdbcTemplate.update(INSERT_CUSTOMER_QUERY,
                customer.getFirstName(), customer.getLastName(), customer.getPhoneNumber(),
                customer.getVatNumber(), customer.getCompany());
        customer.setId(Objects.requireNonNull(id.getKey()).intValue());

        jdbcTemplate.batchUpdate(INSERT_CUSTOMER_EMAIL_QUERY,
                customer.getEmails(), customer.getEmails().size(), (ps, email) -> {
                    ps.setInt(1, customer.getId());
                    ps.setString(2, email);

                });
        return customer;
    }

    @Override
    public List<Customer> getAllCustomer() {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMERS, (rs, rowNum) -> new Customer(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("phone_number"),
                rs.getString("vat_number"),
                rs.getString("company"),
                Arrays.asList(rs.getString("emails").split(", "))
        ));
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        jdbcTemplate.update(INSERT_APPOINTMENT_QUERY, appointment.getCustomer_id(),
                appointment.getUser_id(),
                appointment.getAppointmentDate(),
                appointment.getReminderDate());
        appointment.setId(Objects.requireNonNull(id.getKey()).intValue());
        return appointment;
    }
}
