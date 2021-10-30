package com.example.testproject.service;

import com.example.testproject.domain.Patient;
import com.example.testproject.domain.dto.PatientDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PatientService {

    public List<PatientDTO> showAll();

    public PatientDTO patientToDto(Patient patient);

    public PatientDTO showPatient(Long patientId);

    public Patient showById(Long id);

    public Patient isPatientPresentByPhoneNumberOrId(PatientDTO patientDTO, Long id);

    public Patient isPatientPresentByPhoneNumberOrId(PatientDTO patientDTO);

    public void save(PatientDTO patientDTO);

    public Date dateConverter(LocalDate ld);

    public void update(Long id, PatientDTO updatedPatient);

    public void delete(Long id);
}
