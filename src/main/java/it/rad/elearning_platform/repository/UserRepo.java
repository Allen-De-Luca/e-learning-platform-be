package it.rad.elearning_platform.repository;

import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.User;

import java.util.Optional;

public interface UserRepo {

    Long saveUser(String username, String password);

    Optional<User> findByUsername(String username);

    void addContactUser(Contact contact, Long userId);
}
