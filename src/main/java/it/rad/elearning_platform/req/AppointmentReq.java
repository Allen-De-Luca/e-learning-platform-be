package it.rad.elearning_platform.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentReq {

    private Long appId;
    private Long customerId;
    private Long contactId;
    private LocalDateTime appointmentDate;
    private LocalDate reminderDate;
    private String notes;
}
