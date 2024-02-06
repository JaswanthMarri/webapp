package com.example.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    @JsonIgnore
    @Column(name = "account_created")
    private LocalDateTime accountCreated;

    @JsonIgnore
    @Column(name = "account_updated")
    private LocalDateTime accountUpdated;

}
