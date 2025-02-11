package it.rad.elearning_platform.controllers;

import it.rad.elearning_platform.model.Contact;
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
    User user;
    Contact contact;

    @PostMapping("/user")
    public User addUser(String username, String password, String contactName, String contactLastName, List<String> email){
        contact = new Contact(contactName, contactLastName, email);
        contact = userService.saveContact(contact);
        user = new User(username, password, contact);
        user = userService.saveUser(user);
        return user;
    }
}
