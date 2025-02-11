package it.rad.elearning_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String vatNumber;
    private String company;
    private List<String> emails;

}