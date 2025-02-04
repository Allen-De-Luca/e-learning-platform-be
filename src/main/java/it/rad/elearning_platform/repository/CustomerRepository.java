package it.rad.elearning_platform.repository;

import it.rad.elearning_platform.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
}
