package it.rad.elearning_platform.repository;

import it.rad.elearning_platform.model.User;

import java.util.Optional;

public interface UserRepo {

    Long saveUser(User User);

    Optional<User> findByUsername(String username);
}
