package com.softwaymedical.wms.patient.exceptions;

import jakarta.ws.rs.core.Response;

/**
 * RG08 / CA06 — l'IPP n'est jamais modifiable après création.
 */
public class IppNotModifiableException extends PatientException {

    public IppNotModifiableException() {
        super(Response.Status.BAD_REQUEST, "IPP_NON_MODIFIABLE", "L'IPP n'est pas modifiable après création.");
    }
}
