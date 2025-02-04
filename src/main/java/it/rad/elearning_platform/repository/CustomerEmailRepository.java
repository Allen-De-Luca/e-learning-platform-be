package it.rad.elearning_platform.repository;

import it.rad.elearning_platform.model.CustomerEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerEmailRepository extends JpaRepository<CustomerEmail, Long> {
}
