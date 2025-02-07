package it.rad.elearning_platform.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CustomerEmail {
    private int id;
    private String email;
    private Customer client;
}
