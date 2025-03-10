package it.rad.elearning_platform.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime appointmentDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate reminderDate;
    private String notes;

    public Appointment (Long customerToContactId, LocalDateTime appointmentDate, int days, String notes){
        this.customerToContactId = customerToContactId;
        this.appointmentDate = appointmentDate;
        this.reminderDate = appointmentDate.toLocalDate().minusDays(days);
        this.notes=notes;
    };

}
