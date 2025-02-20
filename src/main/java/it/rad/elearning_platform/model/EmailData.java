package it.rad.elearning_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailData {
    LocalDate appointmentDate;
    LocalDate reminderDate;
    String customerFullName;
    String customerCompany;
    String contactFullName;
    List<String> contactEmail;
}
