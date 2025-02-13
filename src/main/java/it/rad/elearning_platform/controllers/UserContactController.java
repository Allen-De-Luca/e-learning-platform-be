package it.rad.elearning_platform.controllers;

import it.rad.elearning_platform.model.AddUsedReq;
import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.User;
import it.rad.elearning_platform.service.UserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")
public class UserContactController {
    @Autowired
    UserContactService userContactService;
    User user;
    Contact contact;


//    public void addUser(String username, String password, String contactName, String contactLastName, List<String> email){
@PostMapping("/user")
      public void addUser(@RequestBody AddUsedReq addUser){
//        contact = new Contact(contactName, contactLastName, email);
        contact = new Contact(addUser.getFirstName(), addUser.getLastName(), addUser.getEmail());
        contact = userContactService.saveContact(contact);
//        user = new User(username, password, contact);
        user = new User(addUser.getUsername(), addUser.getPassword(), contact);
        user = userContactService.saveUser(user);
    }

//    @GetMapping("/login")
    public boolean authentication(String username, String password){
        return userContactService.checkUser(username, password);
    }

    @GetMapping("/allContacts")
    public List<Contact> getAllContacts(){
        return userContactService.getAllContacts();
    }
}
