package it.rad.elearning_platform.service;

import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.User;
import it.rad.elearning_platform.repository.ContactRepository;
import it.rad.elearning_platform.repository.UserRepository;
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
public class UserContactService implements UserRepository, ContactRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private KeyHolder id;

    private static final String INSERT_USER_QUERY =
            "INSERT INTO user(username, user_password, contact_id) values (?,?,?)";
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
    private static final String CHECK_USER_CREDENTIALS =
            "SELECT COUNT(*) FROM user WHERE username = ? AND user_password = ?";


    @Override
    public User saveUser(User user) {
        id = new GeneratedKeyHolder();
//        jdbcTemplate.update(INSERT_USER_QUERY,
//                user.getUsername(), user.getPassword(), user.getContact().getId());
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

        Number key = id.getKey();
        if (key == null) {
            throw new IllegalStateException("Errore nel recupero della chiave generata per il contatto.");
        }

 //       contact.setId(Objects.requireNonNull(id.getKey()).intValue());
        contact.setId(key.intValue());
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
}
