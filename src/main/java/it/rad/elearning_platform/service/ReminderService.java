package it.rad.elearning_platform.service;

import it.rad.elearning_platform.model.Appointment;
import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.Customer;
import it.rad.elearning_platform.repository.ReminderRepo;
import it.rad.elearning_platform.model.Event;
import it.rad.elearning_platform.rsp.EventListRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public void saveCustomer(String firstName, String lastName,
                             String phoneNumber, String vatNumber,
                             String company, List<String> emails,
                             Long contactId, Long customerId) {

        if(customerId <1 ) {
            id = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        INSERT_CUSTOMER,
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, phoneNumber);
                ps.setString(4, vatNumber);
                ps.setString(5, company);
                return ps;
            }, id);

            Long idAutoGenerated = Objects.requireNonNull(id.getKey()).longValue();

            jdbcTemplate.batchUpdate(INSERT_CUSTOMER_EMAIL,
                    emails, emails.size(), (ps, email) -> {
                        ps.setLong(1, idAutoGenerated);
                        ps.setString(2, email);

                    });

            jdbcTemplate.update(INSERT_CUSTOMER_TO_CONTACT, idAutoGenerated, contactId);

        } else {

            jdbcTemplate.update(UPDATE_CUSTOMER, firstName, lastName, phoneNumber, vatNumber, company, customerId);

        }
    }

    @Override
    public void addCustomerEmail(Long customerId, List<String> emails) {
        jdbcTemplate.batchUpdate(ADD_CUSTOMER_EMAIL_BY_CUSTOMER_ID, emails, emails.size(), (ps, email) ->{
            ps.setLong(1, customerId);
            ps.setString(2, email);
        });
    }

    @Override
    public void deleteCustomerEmail(Long customerId, List<String> emails) {
        jdbcTemplate.batchUpdate(DELETE_CUSTOMER_EMAIL_BY_CUSTOMER_ID, emails, emails.size(), (ps, email) ->{
            ps.setLong(1, customerId);
            ps.setString(2, email);
        });
    }

    @Override
    public Long findCustomerToContactId(Long customerId, Long contactId) {
        return jdbcTemplate.queryForObject(FIND_CUSTOMER_TO_CONTACT_ID,
                Long.class,
                customerId,
                contactId);
    }

    @Override
    public List<Customer> getAllCustomerByContactId(Long contactId) {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMERS_BY_CONTACT_ID, (rs, rowNum) -> {
            String emails = rs.getString("emails");
            List<String> emailList = (emails != null && emails.contains(", "))
                    ? Arrays.asList(emails.split(", "))
                    : (emails != null ? List.of(emails) : new ArrayList<>());

            return new Customer(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone_number"),
                    rs.getString("vat_number"),
                    rs.getString("company"),
                    emailList
            );
        }, contactId);
    }

    @Override
    public Long saveAppointment(Long appointmentId, Long customerId, Long contactId, LocalDateTime appointmentDate, LocalDate reminderDate, String notes) {

        Long customerToContactId = findCustomerToContactId(customerId, contactId);

        if(appointmentId < 1) {
            id = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        INSERT_APPOINTMENT,
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setLong(1, customerToContactId);
                ps.setTimestamp(2, Timestamp.valueOf(appointmentDate)); // Conversione corretta
                ps.setDate(3, Date.valueOf(reminderDate));
                ps.setString(4, notes);
                return ps;
            }, id);

            return Objects.requireNonNull(id.getKey()).longValue();
        } else {

            jdbcTemplate.update(UPDATE_APPOINTMENT, customerToContactId, appointmentDate, reminderDate, notes, appointmentId);

            return appointmentId;
        }
    }

    @Override
    public List<Appointment> getAllAppointmentByCustomer(Long customerId) {
        return jdbcTemplate.query(SELECT_ALL_APPOINTMENT_BY_CUSTOMER_ID, (rs, rowNum) -> new Appointment(
                rs.getLong("appointment_id"),
                rs.getLong("customer_to_contact_id"),
                rs.getTimestamp("appointment_date").toLocalDateTime(),
                rs.getDate("reminder_date").toLocalDate(),
                rs.getString("notes")
                ) , customerId);
    }

    @Override
    public void saveAppointmentNote(Long appointmentId, String note) {
        jdbcTemplate.update(SAVE_APPOINTMENT_NOTE, note, appointmentId);
    }

    @Override
    public EventListRsp getEventsByContactId(Long contactId) {
        List<Event> appointments = new ArrayList<>();
        List<Event> reminders = new ArrayList<>();

        jdbcTemplate.query(GET_ALL_APPOINTMENT_DATE_BY_CONTACT_ID, (rs) -> {
            appointments.add(new Event(rs.getString("company") + " appointment", rs.getString("appointment_date")));
            reminders.add(new Event(rs.getString("company") + " reminder", rs.getString("reminder_date")));
        }, contactId);

        return new EventListRsp(appointments, reminders);
    }

    @Override
    public void deleteAppointmentById(Long appointmentId) {
        jdbcTemplate.update(DELETE_APPOINTMENT_BY_ID, appointmentId);
    }
}
