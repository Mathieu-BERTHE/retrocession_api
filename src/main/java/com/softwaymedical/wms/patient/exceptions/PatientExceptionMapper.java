package com.softwaymedical.wms.patient.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class PatientExceptionMapper implements ExceptionMapper<PatientException> {

    @Override
    public Response toResponse(PatientException exception) {
        return Response.status(exception.getStatus())
                .type(MediaType.APPLICATION_JSON)
                .entity(new ApiError(exception.getCode(), exception.getMessage()))
                .build();
    }
}
