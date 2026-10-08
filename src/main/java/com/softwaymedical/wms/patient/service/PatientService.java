package com.softwaymedical.wms.patient.service;

import com.softwaymedical.wms.patient.api.model.PatientDto;
import com.softwaymedical.wms.patient.exceptions.IppNotModifiableException;
import com.softwaymedical.wms.patient.exceptions.PatientAlreadyExistsException;
import com.softwaymedical.wms.patient.exceptions.PatientDeletionForbiddenException;
import com.softwaymedical.wms.patient.exceptions.PatientNotFoundException;
import com.softwaymedical.wms.patient.model.Patient;
import com.softwaymedical.wms.patient.repository.PatientRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class PatientService {

    @Inject
    PatientRepository patientRepository;

    @Inject
    PatientMapper patientMapper;

    public List<PatientDto> searchGlobal(String terme) {
        List<Patient> patients = terme == null || terme.isBlank()
                ? patientRepository.searchAdvanced(null, null, null, null, null)
                : patientRepository.searchGlobal(terme);
        return patients.stream().map(patientMapper::toDto).toList();
    }

    public List<PatientDto> searchAdvanced(String ipp, String ins, String nom, String prenom, LocalDate dateNaissance) {
        return patientRepository.searchAdvanced(ipp, ins, nom, prenom, dateNaissance).stream()
                .map(patientMapper::toDto)
                .toList();
    }

    public PatientDto findById(Long id) {
        return patientMapper.toDto(getPatient(id));
    }

    @Transactional
    public PatientDto create(PatientDto dto) {
        String ipp = dto.ipp().trim();
        if (patientRepository.findByIpp(ipp).isPresent()) {
            throw PatientAlreadyExistsException.ipp();
        }
        verifierInsDisponible(dto.ins(), null);

        Patient patient = new Patient();
        patient.ipp = ipp;
        patientMapper.updateEntity(dto, patient);
        patientRepository.persistAndFlush(patient);
        return patientMapper.toDto(patient);
    }

    @Transactional
    public PatientDto update(Long id, PatientDto dto) {
        Patient patient = getPatient(id);
        if (!patient.ipp.equals(dto.ipp().trim())) {
            throw new IppNotModifiableException();
        }
        verifierInsDisponible(dto.ins(), patient.id);

        patientMapper.updateEntity(dto, patient);
        patientRepository.flush();
        return patientMapper.toDto(patient);
    }

    @Transactional
    public void delete(Long id) {
        Patient patient = getPatient(id);
        if (patientRepository.countSejours(patient.id) > 0) {
            throw new PatientDeletionForbiddenException();
        }
        patientRepository.delete(patient);
    }

    private Patient getPatient(Long id) {
        return patientRepository.findByIdOptional(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }

    private void verifierInsDisponible(String ins, Long patientId) {
        String insNettoye = PatientMapper.nettoyer(ins);
        if (insNettoye == null) {
            return;
        }
        patientRepository.findByIns(insNettoye)
                .filter(existant -> !existant.id.equals(patientId))
                .ifPresent(existant -> {
                    throw PatientAlreadyExistsException.ins();
                });
    }
}
