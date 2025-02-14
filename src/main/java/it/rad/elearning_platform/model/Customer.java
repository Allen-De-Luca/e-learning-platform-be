package it.rad.elearning_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String vatNumber;
    private String company;
    private List<String> emails;

    public Customer(String firstName, String lastName, String phoneNumber,
                    String vatNumber, String company, List<String> emails) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.vatNumber = vatNumber;
        this.company = company;
        this.emails = emails;
    }
}