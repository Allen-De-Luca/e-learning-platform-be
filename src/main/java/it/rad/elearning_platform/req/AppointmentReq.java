package it.rad.elearning_platform.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentReq {

    private Long customerId;
    private Long contactId;
    private LocalDateTime appointmentDate;
    private int reminderDays;
    private String notes;
}
