package com.softwaymedical.wms.patient.api;

import com.softwaymedical.wms.patient.api.model.PatientDto;
import com.softwaymedical.wms.patient.service.PatientService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Path("/v1/patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientResource {

    @Inject
    PatientService patientService;

    /**
     * Recherche globale : GET /v1/patients?q=terme (sans terme : tous les patients).
     */
    @GET
    public List<PatientDto> searchGlobal(@QueryParam("q") String terme) {
        return patientService.searchGlobal(terme);
    }

    /**
     * Recherche avancée : critères combinés en ET logique, un critère absent est ignoré.
     */
    @GET
    @Path("/search")
    public List<PatientDto> searchAdvanced(@QueryParam("ipp") String ipp,
                                           @QueryParam("ins") String ins,
                                           @QueryParam("nom") String nom,
                                           @QueryParam("prenom") String prenom,
                                           @QueryParam("dateNaissance") LocalDate dateNaissance) {
        return patientService.searchAdvanced(ipp, ins, nom, prenom, dateNaissance);
    }

    @GET
    @Path("/{id}")
    public PatientDto findById(@PathParam("id") Long id) {
        return patientService.findById(id);
    }

    @POST
    public Response create(@Valid PatientDto patient) {
        PatientDto cree = patientService.create(patient);
        return Response.created(URI.create("/api/v1/patients/" + cree.id())).entity(cree).build();
    }

    @PUT
    @Path("/{id}")
    public PatientDto update(@PathParam("id") Long id, @Valid PatientDto patient) {
        return patientService.update(id, patient);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        patientService.delete(id);
        return Response.noContent().build();
    }
}
