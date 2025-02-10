package it.rad.elearning_platform.service;

import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.User;
import it.rad.elearning_platform.repository.ContactEmailRepository;
import it.rad.elearning_platform.repository.ContactRepository;
import it.rad.elearning_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService implements UserRepository, ContactRepository, ContactEmailRepository {

    @Autowired
    private JdbcTemplate JdbcTemplate;
    private final KeyHolder id = new GeneratedKeyHolder();

    private static final String INSERT_USER_QUERY=
            "INSERT INTO user(username, user_password, contact_id) values (?,?,?,?)";
    private static final String INSERT_CONTACT_QUERY=
            "INSERT INTO contact(first_name, last_name) values (?,?,?)";
    private static final String INSERT_CONTACT_EMAIL_QUERY=
            "INSERT INTO contact_email(contact_id, email) values (?,?)";



    @Override
    public User saveUser(User user) {
        JdbcTemplate.update(INSERT_USER_QUERY,
                user.getUsername(), user.getPassword(), user.getContact().getId());
        user.setId(Objects.requireNonNull(id.getKey()).intValue());
        return user;
    }

    @Override
    public Contact saveContact(Contact contact) {
        JdbcTemplate.update(INSERT_CONTACT_QUERY,
                contact.getFirstName(), contact.getLastName());
        contact.setId(Objects.requireNonNull(id.getKey()).intValue());
        JdbcTemplate.batchUpdate(INSERT_CONTACT_EMAIL_QUERY,
                contact.getEmails(), contact.getEmails().size(), (ps, email) -> {
                    ps.setInt(1, contact.getId());
                    ps.setString(2, email);

                });
        return contact;
    }


    @Override
    public List<Contact> getAllContacts() {
        return List.of();
    }
}
