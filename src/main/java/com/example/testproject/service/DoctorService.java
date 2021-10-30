package com.example.testproject.service;

import com.example.testproject.domain.Doctor;
import com.example.testproject.domain.dto.DoctorDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface DoctorService {

    public List<DoctorDTO> showAll();

    public Doctor showById(Long id);

    public DoctorDTO doctorToDto(Doctor doctor);

    public void save(DoctorDTO doctorDTO);

    public Date dateConverter(LocalDate ld);

    public DoctorDTO showDoctor(Long doctorId);

    public Doctor isDoctorPresentByPhoneNumberOrId(DoctorDTO doctorDTO, Long id);

    public Doctor isDoctorPresentByPhoneNumberOrId(DoctorDTO doctorDTO);

    public void update(Long id, DoctorDTO updatedPerson);

    public void delete(Long id);
}
