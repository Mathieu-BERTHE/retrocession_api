package com.softwaymedical.wms.patient.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient")
public class Patient {

    public enum Sexe {M, F, I}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true, nullable = false, updatable = false)
    public String ipp;

    @Column(nullable = false)
    public String nomNaissance;

    @Column(nullable = false)
    public String prenom;

    @Column(nullable = false)
    public LocalDate dateNaissance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    public Sexe sexe;

    @Column(unique = true)
    public String ins;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    public LocalDateTime createdAt;

    public String nomUsage;
    public String adresse;
    public String complementAdresse;
    public String codePostal;
    public String ville;
    public String telephone;
    public String email;
}
