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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserContactService implements UserRepository, ContactRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final KeyHolder id = new GeneratedKeyHolder();

    private static final String INSERT_USER_QUERY=
            "INSERT INTO user(username, user_password, contact_id) values (?,?,?,?)";
    private static final String INSERT_CONTACT_QUERY=
            "INSERT INTO contact(first_name, last_name) values (?,?,?)";
    private static final String INSERT_CONTACT_EMAIL_QUERY=
            "INSERT INTO contact_email(contact_id, email) values (?,?)";

    private static final String SELECT_ALL_CONTACTS=
            "SELECT c.id, c.first_name, c.last_name, " +
                    "GROUP_CONCAT(ce.email SEPARATOR ', ') AS emails " +
                    "FROM contact c " +
                    "LEFT JOIN contact_email ce ON c.id = ce.contact_id " +
                    "GROUP BY c.id, c.first_name, c.last_name";
    private static final String CHECK_USER_CREDENTIALS=
            "SELECT COUNT(*) FROM user WHERE username = ? AND user_password = ?";



    @Override
    public User saveUser(User user) {
        jdbcTemplate.update(INSERT_USER_QUERY,
                user.getUsername(), user.getPassword(), user.getContact().getId());
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
        jdbcTemplate.update(INSERT_CONTACT_QUERY,
                contact.getFirstName(), contact.getLastName());
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
}
