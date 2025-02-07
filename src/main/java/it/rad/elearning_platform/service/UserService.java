package it.rad.elearning_platform.service;

import it.rad.elearning_platform.model.User;
import it.rad.elearning_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserService implements UserRepository {

    @Autowired
    private JdbcTemplate JdbcTemplate;

    private static final String INSERT_USER_QUERY= "INSERT INTO user(id, username, user_password, contact_id) values (?,?,?,?)";

    @Override
    public void save(User user) {
        JdbcTemplate.update(INSERT_USER_QUERY,user.getId(), user.getUsername(), user.getPassword(), user.getContact().getId());
    }
}
