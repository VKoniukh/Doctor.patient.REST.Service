package com.example.testproject.repository;

import com.example.testproject.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    public Optional<Doctor> findDoctorByPhoneNumber(long phoneNumber);

    public Optional<Doctor> findDoctorById(Long id);
}
