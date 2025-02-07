package it.rad.elearning_platform.model;

import lombok.Data;
import java.util.List;

@Data
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String vatNumber;
    private String company;
    private List<CustomerEmail> emails;

}