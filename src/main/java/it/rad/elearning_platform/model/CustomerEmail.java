package it.rad.elearning_platform.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CustomerEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer client;
}
