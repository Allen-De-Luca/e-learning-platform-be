package it.rad.elearning_platform.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Contact {
    private int id;
    private String firstName;
    private String lastName;
    private List<String> emails;

    public Contact(String name, String lastName, List<String> emails) {
        this.firstName=name;
        this.lastName=lastName;
        this.emails=emails;
    }
}