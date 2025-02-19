package it.rad.elearning_platform.service;

import it.rad.elearning_platform.model.User;
import it.rad.elearning_platform.repository.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

import static it.rad.elearning_platform.constants.Query.CHECK_USER_CREDENTIALS;
import static it.rad.elearning_platform.constants.Query.INSERT_USER_QUERY;

public class AuthService implements AuthRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;
    KeyHolder id;

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
}
