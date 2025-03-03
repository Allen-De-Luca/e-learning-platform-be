package it.rad.elearning_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Appointment {
    private Long id;
    private Long customerToContactId;
    private LocalDateTime appointmentDate;
    private LocalDate reminderDate;
    private String notes;

    public Appointment (Long customerToContactId, LocalDateTime appointmentDate, int days, String notes){
        this.customerToContactId = customerToContactId;
        this.appointmentDate = appointmentDate;
        this.reminderDate = appointmentDate.toLocalDate().minusDays(days);
        this.notes=notes;
    };

}
