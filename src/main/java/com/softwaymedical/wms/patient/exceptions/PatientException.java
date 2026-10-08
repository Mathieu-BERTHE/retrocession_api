package com.softwaymedical.wms.patient.exceptions;

import jakarta.ws.rs.core.Response;

/**
 * Exception métier du domaine patient : porte le statut HTTP et un code fonctionnel
 * renvoyés au client par {@link PatientExceptionMapper}.
 */
public class PatientException extends RuntimeException {

    private final Response.Status status;
    private final String code;

    protected PatientException(Response.Status status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public Response.Status getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }
}
