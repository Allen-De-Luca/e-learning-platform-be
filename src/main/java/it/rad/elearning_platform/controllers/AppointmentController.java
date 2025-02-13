package it.rad.elearning_platform.controllers;

import it.rad.elearning_platform.model.Appointment;
import it.rad.elearning_platform.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;
    Appointment appointment;

    @PostMapping("/addAppointment")
    public void saveAppointment(int customer_id, int user_id, LocalDate appointmentDate, int days){
        appointment = new Appointment(customer_id, user_id, appointmentDate, days);
        appointment = appointmentService.saveAppointment(appointment);
    }
}
