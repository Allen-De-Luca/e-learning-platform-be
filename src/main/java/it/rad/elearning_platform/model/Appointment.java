package it.rad.elearning_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Appointment {
    private Long id;
    private Long customerId;
    private Long userId;
    private LocalDate appointmentDate;
    private LocalDate reminderDate;

    public Appointment (Long customerId, Long userId, LocalDate appointmentDate, int days){
        this.customerId = customerId;
        this.userId = userId;
        this.appointmentDate = appointmentDate;
        this.reminderDate = appointmentDate.minusDays(days);
    };

}
