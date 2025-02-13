package it.rad.elearning_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AddUsedReq {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private List<String> email;
}
