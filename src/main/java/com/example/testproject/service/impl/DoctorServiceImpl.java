package com.example.testproject.service.impl;

import com.example.testproject.domain.Doctor;
import com.example.testproject.domain.Patient;
import com.example.testproject.domain.dto.DoctorDTO;
import com.example.testproject.repository.DoctorRepository;
import com.example.testproject.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    private final PatientServiceImpl patientService;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, PatientServiceImpl patientService) {
        this.doctorRepository = doctorRepository;
        this.patientService = patientService;
    }

    @Override
    public List<DoctorDTO> showAll() {
        List<Doctor> allDocList = new ArrayList<>(doctorRepository.findAll());
        List<DoctorDTO> allDocDtoList = new ArrayList<>();
        for (Doctor doc : allDocList) {
            allDocDtoList.add(doctorToDto(doc));
        }
        return allDocDtoList;
    }

    @Override
    public Doctor isDoctorPresentByPhoneNumberOrId(DoctorDTO doctorDTO, Long id) {
        if (doctorRepository.findDoctorByPhoneNumber(doctorDTO.getPhoneNumber()).isPresent()) {
            return doctorRepository.findDoctorByPhoneNumber(doctorDTO.getPhoneNumber()).get();
        } else if (doctorRepository.findDoctorById(id).isPresent()) {
            return doctorRepository.findDoctorById(id).get();
        }
        return null;
    }

    @Override
    public Doctor isDoctorPresentByPhoneNumberOrId(DoctorDTO doctorDTO) {
        if (doctorRepository.findDoctorByPhoneNumber(doctorDTO.getPhoneNumber()).isPresent()) {
            return doctorRepository.findDoctorByPhoneNumber(doctorDTO.getPhoneNumber()).get();
        }
        return null;
    }

    @Override
    public Doctor showById(Long id) {
        if (doctorRepository.findDoctorById(id).isPresent()) {
            return doctorRepository.findDoctorById(id).get();
        }
        return null;
    }

    @Override
    public DoctorDTO doctorToDto(Doctor doctor) {
        DoctorDTO doctorDTO = DoctorDTO.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .patronymic(doctor.getPatronymic())
                .position(doctor.getPosition())
                .birthDate(doctor.getBirthDate().toString())
                .phoneNumber(doctor.getPhoneNumber())
                .build();
        return doctorDTO;
    }

    @Override
    public void save(DoctorDTO doctorDTO) {
        LocalDate ld = LocalDate.parse(doctorDTO.getBirthDate());
        Doctor doctor = Doctor.builder()
                .firstName(doctorDTO.getFirstName())
                .lastName(doctorDTO.getLastName())
                .patronymic(doctorDTO.getPatronymic())
                .position(doctorDTO.getPosition())
                .birthDate(dateConverter(ld))
                .phoneNumber(doctorDTO.getPhoneNumber())
                .build();

        doctorRepository.save(doctor);
    }

    public DoctorDTO showDoctor(Long doctorId) {
        DoctorDTO doctorDTO = DoctorDTO.builder()
                .firstName(doctorRepository.getById(doctorId).getFirstName())
                .lastName(doctorRepository.getById(doctorId).getLastName())
                .patronymic(doctorRepository.getById(doctorId).getPatronymic())
                .position(doctorRepository.getById(doctorId).getPosition())
                .birthDate(doctorRepository.getById(doctorId).getBirthDate().toString())
                .phoneNumber(doctorRepository.getById(doctorId).getPhoneNumber())
                .build();
        return doctorDTO;
    }

    @Override
    public Date dateConverter(LocalDate ld) {
        return java.sql.Date.valueOf(ld);
    }

    @Override
    public void update(Long id, DoctorDTO updatedDoctor) {
        LocalDate ld = LocalDate.parse(updatedDoctor.getBirthDate());
        if (showById(id) != null) {
            Doctor doctorFromDb = showById(id);
            doctorFromDb.setFirstName(updatedDoctor.getFirstName());
            doctorFromDb.setLastName(updatedDoctor.getLastName());
            doctorFromDb.setPatronymic(updatedDoctor.getPatronymic());
            doctorFromDb.setBirthDate(dateConverter(ld));
            doctorFromDb.setPosition(updatedDoctor.getPosition());
            doctorFromDb.setPhoneNumber(updatedDoctor.getPhoneNumber());
            doctorRepository.save(doctorFromDb);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (showById(id).getPatients().isEmpty()) {
                doctorRepository.existsById(id);
                doctorRepository.deleteById(id);
            } else {
                List<Patient> patientList = new ArrayList<>(showById(id).getPatients());
                for (Patient patient : patientList)
                    patientService.delete(patient.getId());

                doctorRepository.existsById(id);
                doctorRepository.deleteById(id);
            }
        } catch (EntityNotFoundException exception) {
            System.out.println("Unfortunately, there was an error:" + exception.getMessage());
        }
    }
}
