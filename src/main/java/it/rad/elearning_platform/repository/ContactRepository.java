package it.rad.elearning_platform.repository;

import it.rad.elearning_platform.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}