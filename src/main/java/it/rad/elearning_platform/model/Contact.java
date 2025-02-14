package it.rad.elearning_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Contact {
    private Long id;
    private String firstName;
    private String lastName;
    private List<String> emails;

    public Contact(String name, String lastName, List<String> emails) {
        this.firstName=name;
        this.lastName=lastName;
        this.emails=emails;
    }
}