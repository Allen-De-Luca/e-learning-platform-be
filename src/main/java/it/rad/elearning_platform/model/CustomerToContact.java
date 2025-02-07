package it.rad.elearning_platform.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CustomerToContact {
    private int id;
    private Customer customer;
    private Contact contact;
}