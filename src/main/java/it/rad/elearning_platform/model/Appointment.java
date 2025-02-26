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
    private Long customerId;
    private Long contactId;
    private LocalDateTime appointmentDate;
    private LocalDateTime reminderDate;

    public Appointment (Long customerId, Long contactId, LocalDateTime appointmentDate, int days){
        this.customerId = customerId;
        this.contactId = contactId;
        this.appointmentDate = appointmentDate;
        this.reminderDate = appointmentDate.minusDays(days);
    };

}
