package it.rad.elearning_platform.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
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
