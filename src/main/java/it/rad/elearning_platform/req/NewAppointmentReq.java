package it.rad.elearning_platform.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewAppointmentReq {

    private Long customerId;
    private Long userId;
    private LocalDate appointmentDate;
    private int reminderDays;

}
