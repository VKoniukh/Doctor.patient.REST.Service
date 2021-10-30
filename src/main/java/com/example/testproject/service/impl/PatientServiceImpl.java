package com.example.testproject.service.impl;


import com.example.testproject.domain.Patient;
import com.example.testproject.domain.dto.PatientDTO;
import com.example.testproject.repository.DoctorRepository;
import com.example.testproject.repository.PatientRepository;
import com.example.testproject.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    private final DoctorRepository doctorRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<PatientDTO> showAll() {
        List<Patient> allPatientsList = new ArrayList<>(patientRepository.findAll());
        List<PatientDTO> allPatientsDtoList = new ArrayList<>();
        for (Patient patient : allPatientsList) {
            allPatientsDtoList.add(patientToDto(patient));
        }
        return allPatientsDtoList;
    }

    public PatientDTO patientToDto(Patient patient) {
        PatientDTO patientDTO = PatientDTO.builder()
                .patientId(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .patronymic(patient.getPatronymic())
                .doctorId(patient.getDoctor().getId())
                .birthDate(patient.getBirthDate().toString())
                .phoneNumber(patient.getPhoneNumber())
                .build();
        return patientDTO;
    }

    @Override
    public Patient showById(Long id) {
        return patientRepository.getById(id);
    }

    @Override
    public Patient isPatientPresentByPhoneNumberOrId(PatientDTO patientDTO, Long id) {
        if (patientRepository.findPatientByPhoneNumber(patientDTO.getPhoneNumber()).isPresent()) {
            return patientRepository.findPatientByPhoneNumber(patientDTO.getPhoneNumber()).get();
        } else if (patientRepository.findPatientById(id).isPresent()) {
            return patientRepository.findPatientById(id).get();
        }
        return null;
    }

    @Override
    public Patient isPatientPresentByPhoneNumberOrId(PatientDTO patientDTO) {
        if (patientRepository.findPatientByPhoneNumber(patientDTO.getPhoneNumber()).isPresent()) {
            return patientRepository.findPatientByPhoneNumber(patientDTO.getPhoneNumber()).get();
        }
        return null;
    }

    @Override
    public PatientDTO showPatient(Long patientId) {
        PatientDTO patientDTO = PatientDTO.builder()
                .patientId(patientRepository.getById(patientId).getId())
                .firstName(patientRepository.getById(patientId).getFirstName())
                .lastName(patientRepository.getById(patientId).getLastName())
                .patronymic(patientRepository.getById(patientId).getPatronymic())
                .doctorId(patientRepository.getById(patientId).getDoctor().getId())
                .birthDate(patientRepository.getById(patientId).getBirthDate().toString())
                .phoneNumber(patientRepository.getById(patientId).getPhoneNumber())
                .build();
        return patientDTO;
    }

    @Override
    public void save(PatientDTO patientDTO) {
        LocalDate ld = LocalDate.parse(patientDTO.getBirthDate());
        Patient patient = Patient.builder()
                .firstName(patientDTO.getFirstName())
                .lastName(patientDTO.getLastName())
                .patronymic(patientDTO.getPatronymic())
                .doctor(doctorRepository.getById(patientDTO.getDoctorId()))
                .birthDate(dateConverter(ld))
                .phoneNumber(patientDTO.getPhoneNumber())
                .build();

        patientRepository.save(patient);
    }

    @Override
    public Date dateConverter(LocalDate ld) {
        return java.sql.Date.valueOf(ld);
    }

    @Override
    public void update(Long id, PatientDTO updatedPatient) {
        LocalDate ld = LocalDate.parse(updatedPatient.getBirthDate());
        if (showById(id) != null) {
            Patient patientFromDb = showById(id);
            patientFromDb.setFirstName(updatedPatient.getFirstName());
            patientFromDb.setLastName(updatedPatient.getLastName());
            patientFromDb.setPatronymic(updatedPatient.getPatronymic());
            patientFromDb.setBirthDate(dateConverter(ld));
            patientFromDb.setDoctor(doctorRepository.getById(updatedPatient.getDoctorId()));
            patientFromDb.setPhoneNumber(updatedPatient.getPhoneNumber());
            patientRepository.save(patientFromDb);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            patientRepository.existsById(id);
            patientRepository.deleteById(id);
        } catch (EntityNotFoundException exception) {
            System.out.println("Unfortunately, there was an error:" + exception.getMessage());
        }
    }
}
