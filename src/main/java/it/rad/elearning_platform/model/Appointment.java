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
    private int customer_id;
    private int user_id;
    private LocalDate appointmentDate;
    private LocalDate reminderDate;

    public Appointment (int customer_id, int user_id, LocalDate appointmentDate, int days){
        this.customer_id = customer_id;
        this.user_id = user_id;
        this.appointmentDate = appointmentDate;
        this.reminderDate = appointmentDate.minusDays(days);
    };

}
