package it.rad.elearning_platform.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ContactEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;
}