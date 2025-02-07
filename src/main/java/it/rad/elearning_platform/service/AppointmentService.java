package it.rad.elearning_platform.service;

import it.rad.elearning_platform.model.Appointment;
import it.rad.elearning_platform.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class AppointmentService implements AppointmentRepository {

    @Autowired
    private JdbcTemplate JdbcTemplate;

    private static final String INSERT_APPOINTMENT_QUERY= "INSERT INTO appointment(id, customer_id, appointment_date, reminderDate, user_id)";

    @Override
    public void save(Appointment appointment) {
        JdbcTemplate.update(INSERT_APPOINTMENT_QUERY, appointment.getId(), appointment.getCustomer().getId(), appointment.getAppointmentDate(), appointment.getReminderDate(), appointment.getUser().getId());
    }
}
