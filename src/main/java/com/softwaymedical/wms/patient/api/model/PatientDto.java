package com.softwaymedical.wms.patient.api.model;

import com.softwaymedical.wms.patient.model.Patient.Sexe;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Patient exposé aux clients. id et createdAt sont en lecture seule (ignorés en entrée).
 */
public record PatientDto(
        Long id,
        @NotBlank @Size(max = 20) String ipp,
        @Size(max = 255) String ins,
        @NotBlank @Size(max = 100) String nomNaissance,
        @Size(max = 100) String nomUsage,
        @NotBlank @Size(max = 100) String prenom,
        @NotNull LocalDate dateNaissance,
        @NotNull Sexe sexe,
        @Size(max = 255) String adresse,
        @Size(max = 255) String complementAdresse,
        @Size(max = 10) String codePostal,
        @Size(max = 100) String ville,
        @Size(max = 20) String telephone,
        @Email @Size(max = 255) String email,
        LocalDateTime createdAt
) {
}
