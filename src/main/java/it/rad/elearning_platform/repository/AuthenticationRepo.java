package it.rad.elearning_platform.repository;

import it.rad.elearning_platform.model.User;

public interface AuthenticationRepo {
    User saveUser(User User);

    Boolean checkUser(String username, String password);
}
