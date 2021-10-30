package com.example.testproject.repository;

import com.example.testproject.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    public Optional<Patient> findPatientByPhoneNumber(long phoneNumber);

    public Optional<Patient> findPatientById(Long id);
}
