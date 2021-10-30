package com.example.testproject.controller;

import com.example.testproject.domain.Doctor;
import com.example.testproject.domain.dto.DoctorDTO;
import com.example.testproject.service.impl.DoctorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/doctors/")
public class DoctorController {

    private final DoctorServiceImpl doctorServiceImpl;

    @Autowired
    public DoctorController(DoctorServiceImpl doctorServiceImpl) {
        this.doctorServiceImpl = doctorServiceImpl;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<DoctorDTO> getDoctor(@PathVariable("id") Long doctorId) {
        if (doctorId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        DoctorDTO doctorDTO = this.doctorServiceImpl.showDoctor(doctorId);

        if (doctorServiceImpl.isDoctorPresentByPhoneNumberOrId(doctorDTO) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DoctorDTO> saveDoctor(@RequestBody @Valid DoctorDTO doctorDTO) {

        if (doctorServiceImpl.isDoctorPresentByPhoneNumberOrId(doctorDTO) != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.doctorServiceImpl.save(doctorDTO);
        DoctorDTO doctorDTO2 = doctorServiceImpl.showDoctor(doctorServiceImpl.isDoctorPresentByPhoneNumberOrId(doctorDTO).getId());
        return new ResponseEntity<>(doctorDTO2, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<DoctorDTO> updatePatient(@PathVariable("id") Long id, @RequestBody @Valid DoctorDTO doctorDTO) {

        if (doctorServiceImpl.isDoctorPresentByPhoneNumberOrId(doctorDTO, id) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.doctorServiceImpl.update(id, doctorDTO);
        DoctorDTO doctorDTO2 = doctorServiceImpl.showDoctor(doctorServiceImpl.isDoctorPresentByPhoneNumberOrId(doctorDTO).getId());
        return new ResponseEntity<>(doctorDTO2, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Doctor> deletePatient(@PathVariable("id") Long id) {
        Doctor doctor = this.doctorServiceImpl.showById(id);

        if (doctor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.doctorServiceImpl.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllPatients() {
        List<DoctorDTO> doctorDtoList = this.doctorServiceImpl.showAll();

        if (doctorDtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(doctorDtoList, HttpStatus.OK);
    }
}
