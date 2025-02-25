package it.rad.elearning_platform.service;

import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.User;
import it.rad.elearning_platform.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;

import static it.rad.elearning_platform.constants.Query.*;

@Service
public class UserService implements UserRepo{

    @Autowired
    JdbcTemplate jdbcTemplate;
    KeyHolder id;

    @Override
    public Long saveUser(String username, String password) {
        id = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    INSERT_USER_QUERY,
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, username);
            ps.setString(2, password);
            return ps;
        }, id);

        return Objects.requireNonNull(id.getKey()).longValue();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    FIND_USER_BY_USERNAME,
                    (rs, rowNum) -> new User(
                            rs.getLong("id"),
                            rs.getString("username"),
                            rs.getString("user_password")
                    ),
                    username
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void addContactUser(Contact contact, Long userId) {
        id = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    ADD_CONTACT,
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, contact.getFirstName());
            ps.setString(2, contact.getLastName());
            return ps;
        }, id);

        Long contactId = Objects.requireNonNull(id.getKey()).longValue();
        jdbcTemplate.batchUpdate(ADD_CONTACT_EMAIL,
                contact.getEmails(), contact.getEmails().size(), (ps, email) -> {
                    ps.setLong(1, contactId);
                    ps.setString(2, email);
                }
        );

        jdbcTemplate.update(UPDATE_USER_WITH_CONTACT_ID, contactId, userId);

    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        return jdbcTemplate.queryForObject(FIND_USER_BY_USERNAME, new Object[]{username}, rs -> {
//            if (!rs.next()) {
//                throw new UsernameNotFoundException("User not found with username: " + username);
//            }
//            return new CustomUserDetails(
//                    rs.getLong("id"),
//                    rs.getString("username"),
//                    rs.getString("user_password")
//            );
//        });
//    }
}
