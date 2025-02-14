package it.rad.elearning_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class User {
    private Long id;
    private String username;
    private String password;
    private Contact contact;

    public User(String username, String pass, Contact c){
        this.username=username;
        this.password=pass;
        this.contact=c;
    }
}
