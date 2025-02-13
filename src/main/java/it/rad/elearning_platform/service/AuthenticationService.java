package it.rad.elearning_platform.service;

import it.rad.elearning_platform.model.User;
import it.rad.elearning_platform.repository.AuthenticationRepo;

public class AuthenticationService implements AuthenticationRepo {

    @Override
    public User saveUser(User User) {
        return null;
    }

    @Override
    public Boolean checkUser(String username, String password) {
        return null;
    }
}
