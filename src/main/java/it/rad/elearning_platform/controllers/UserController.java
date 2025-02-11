package it.rad.elearning_platform.controllers;

import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.User;
import it.rad.elearning_platform.service.UserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserContactService userContactService;
    User user;
    Contact contact;

    @PostMapping("/user")
    public User addUser(String username, String password, String contactName, String contactLastName, List<String> email){
        contact = new Contact(contactName, contactLastName, email);
        contact = userContactService.saveContact(contact);
        user = new User(username, password, contact);
        user = userContactService.saveUser(user);
        return user;
    }

    @PostMapping("/allContacts")
    public List<Contact> getAllContact(){
        return userContactService.getAllContacts();
    }
}
