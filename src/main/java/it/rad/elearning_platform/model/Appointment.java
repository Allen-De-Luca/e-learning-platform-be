package it.rad.elearning_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Appointment {
    private int id;
    private LocalDate appointmentDate;
    private LocalDate reminderDate;
    private Customer customer;
    private User user;

    public Appointment (LocalDate appointmentDate, int days, Customer customer, User user){
        this.appointmentDate = appointmentDate;
        this.reminderDate = appointmentDate.minusDays(days);
        this.customer = customer;
        this.user = user;
    };

}
