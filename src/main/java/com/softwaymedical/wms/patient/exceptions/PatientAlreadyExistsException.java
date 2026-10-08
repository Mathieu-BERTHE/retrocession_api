package com.softwaymedical.wms.patient.exceptions;

import jakarta.ws.rs.core.Response;

/**
 * RG01 / CA02 — l'IPP (et l'INS) identifient de manière unique un patient.
 */
public class PatientAlreadyExistsException extends PatientException {

    private PatientAlreadyExistsException(String code, String message) {
        super(Response.Status.CONFLICT, code, message);
    }

    public static PatientAlreadyExistsException ipp() {
        return new PatientAlreadyExistsException("IPP_EXISTANT", "Cet IPP existe déjà.");
    }

    public static PatientAlreadyExistsException ins() {
        return new PatientAlreadyExistsException("INS_EXISTANT", "Cet INS est déjà attribué à un autre patient.");
    }
}
