package it.rad.elearning_platform.service;

import it.rad.elearning_platform.model.Appointment;
import it.rad.elearning_platform.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Objects;

public class AppointmentService implements AppointmentRepository {

    @Autowired
    private JdbcTemplate JdbcTemplate;
    private final KeyHolder id = new GeneratedKeyHolder();

    private static final String INSERT_APPOINTMENT_QUERY=
            "INSERT INTO appointment(customer_id, user_id, appointment_date, reminderDate)" +
                    "VALUES (?,?,?,?)";

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        JdbcTemplate.update(INSERT_APPOINTMENT_QUERY, appointment.getCustomer_id(),
                appointment.getUser_id(),
                appointment.getAppointmentDate(),
                appointment.getReminderDate());
        appointment.setId(Objects.requireNonNull(id.getKey()).intValue());
        return appointment;
    }
}
