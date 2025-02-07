package it.rad.elearning_platform.service;

import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Objects;

public class ContactService implements ContactRepository {

    @Autowired
    private org.springframework.jdbc.core.JdbcTemplate JdbcTemplate;

    private static final String INSERT_CONTACT_QUERY= "INSERT INTO contact(id, first_name, last_name) values (?,?,?)";


    @Override
    public int saveContact(Contact contact) {
        KeyHolder contact_id = new GeneratedKeyHolder();
        JdbcTemplate.update(INSERT_CONTACT_QUERY, contact.getFirstName(), contact.getLastName());
        return Objects.requireNonNull(contact_id.getKey()).intValue();
    }

    @Override
    public List<Contact> getAllContacts() {
        return List.of();
    }
}
