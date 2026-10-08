package com.softwaymedical.wms.resource;

import com.softwaymedical.wms.model.Patient;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/patient")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class PatientResource {

    @GET
    public List<Patient> getPatient(){
        return Patient.listAll();
    }

    @GET
    @Path("/{id}")
    public Patient getPatientById(@PathParam("id") long id){
        Patient patient = Patient.findById(id);
        if (patient == null) {
            throw new NotFoundException("Patient " + id + " introuvable");
        }
        return patient;
    }

    @POST
    @Transactional
    public Response addPatient(Patient patient) {
        patient.id = null;
        try {
            patient.persist();
        } catch (Exception ex) {
            throw new RuntimeException("Patient déjà existant");
        }
        return Response.created(URI.create("/patient/" +patient.id)).entity(patient).build();
    }

}
