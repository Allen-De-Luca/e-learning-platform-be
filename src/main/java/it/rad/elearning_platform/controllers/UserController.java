package it.rad.elearning_platform.controllers;

import it.rad.elearning_platform.model.User;
import it.rad.elearning_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/user")
    public User addUser(String username, String Password, String contactName, String contactLastName, List<String> email){
        return new User();
    }
}
