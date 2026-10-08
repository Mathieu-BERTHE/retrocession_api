package com.softwaymedical.wms.patient.service;

import com.softwaymedical.wms.patient.api.model.PatientDto;
import com.softwaymedical.wms.patient.model.Patient;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PatientMapper {

    public PatientDto toDto(Patient patient) {
        return new PatientDto(
                patient.id,
                patient.ipp,
                patient.ins,
                patient.nomNaissance,
                patient.nomUsage,
                patient.prenom,
                patient.dateNaissance,
                patient.sexe,
                patient.adresse,
                patient.complementAdresse,
                patient.codePostal,
                patient.ville,
                patient.telephone,
                patient.email,
                patient.createdAt
        );
    }

    /**
     * Copie les champs modifiables du DTO dans l'entité (hors id, ipp et createdAt).
     * Les chaînes vides sont enregistrées à null (l'INS est soumis à une contrainte d'unicité).
     */
    public void updateEntity(PatientDto dto, Patient patient) {
        patient.ins = nettoyer(dto.ins());
        patient.nomNaissance = nettoyer(dto.nomNaissance());
        patient.nomUsage = nettoyer(dto.nomUsage());
        patient.prenom = nettoyer(dto.prenom());
        patient.dateNaissance = dto.dateNaissance();
        patient.sexe = dto.sexe();
        patient.adresse = nettoyer(dto.adresse());
        patient.complementAdresse = nettoyer(dto.complementAdresse());
        patient.codePostal = nettoyer(dto.codePostal());
        patient.ville = nettoyer(dto.ville());
        patient.telephone = nettoyer(dto.telephone());
        patient.email = nettoyer(dto.email());
    }

    static String nettoyer(String valeur) {
        return valeur == null || valeur.isBlank() ? null : valeur.trim();
    }
}
