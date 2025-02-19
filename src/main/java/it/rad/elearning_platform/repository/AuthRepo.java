package it.rad.elearning_platform.repository;

import it.rad.elearning_platform.model.User;

public interface AuthRepo {

    User saveUser(User User);

    User checkUser(String username, String password);
}
