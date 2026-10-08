package com.softwaymedical.wms.patient.exceptions;

import jakarta.ws.rs.core.Response;

public class PatientNotFoundException extends PatientException {

    public PatientNotFoundException(Long id) {
        super(Response.Status.NOT_FOUND, "PATIENT_INTROUVABLE", "Patient " + id + " introuvable.");
    }
}
