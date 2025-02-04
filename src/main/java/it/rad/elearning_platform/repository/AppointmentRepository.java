package it.rad.elearning_platform.repository;

import it.rad.elearning_platform.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
}
