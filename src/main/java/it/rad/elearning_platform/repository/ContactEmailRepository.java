package it.rad.elearning_platform.repository;

import it.rad.elearning_platform.model.ContactEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactEmailRepository extends JpaRepository<ContactEmail, Long> {
}