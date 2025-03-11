package it.rad.elearning_platform.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCustomerReq {

    private Long customerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String vatNumber;
    private String company;
    private List<String> email;
}
