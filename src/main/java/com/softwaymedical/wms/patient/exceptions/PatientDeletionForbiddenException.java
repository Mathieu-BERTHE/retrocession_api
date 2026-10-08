package com.softwaymedical.wms.patient.exceptions;

import jakarta.ws.rs.core.Response;

/**
 * RG11 / CA13 — la suppression physique est soumise aux règles de conservation des données.
 */
public class PatientDeletionForbiddenException extends PatientException {

    public PatientDeletionForbiddenException() {
        super(Response.Status.CONFLICT, "SUPPRESSION_INTERDITE",
                "La suppression physique est impossible : ce patient possède des données devant être conservées.");
    }
}
