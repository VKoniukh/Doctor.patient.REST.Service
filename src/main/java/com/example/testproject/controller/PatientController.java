package com.example.testproject.controller;

import com.example.testproject.domain.Patient;
import com.example.testproject.domain.dto.PatientDTO;
import com.example.testproject.service.impl.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/v1/patients/")
public class PatientController {

    private final PatientServiceImpl patientServiceImpl;

    @Autowired
    public PatientController(PatientServiceImpl patientServiceImpl) {
        this.patientServiceImpl = patientServiceImpl;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<PatientDTO> getDoctor(@PathVariable("id") Long patientId) {
        if (patientId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        PatientDTO patientDTO = this.patientServiceImpl.showPatient(patientId);

        if (patientServiceImpl.isPatientPresentByPhoneNumberOrId(patientDTO) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(patientDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PatientDTO> saveDoctor(@RequestBody @Valid PatientDTO patientDTO) {

        if (patientServiceImpl.isPatientPresentByPhoneNumberOrId(patientDTO) != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.patientServiceImpl.save(patientDTO);
        PatientDTO patientDTO2 = patientServiceImpl.showPatient(patientServiceImpl.isPatientPresentByPhoneNumberOrId(patientDTO).getId());
        return new ResponseEntity<>(patientDTO2, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable("id") Long id, @RequestBody @Valid PatientDTO patientDTO) {

        if (patientServiceImpl.isPatientPresentByPhoneNumberOrId(patientDTO, id) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.patientServiceImpl.update(id, patientDTO);
        PatientDTO patientDTO2 = patientServiceImpl.showPatient(patientServiceImpl.isPatientPresentByPhoneNumberOrId(patientDTO).getId());
        return new ResponseEntity<>(patientDTO2, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Patient> deletePatient(@PathVariable("id") Long id) {
        Patient patient = this.patientServiceImpl.showById(id);

        if (patient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.patientServiceImpl.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        List<PatientDTO> patientDTOList = this.patientServiceImpl.showAll();

        if (patientDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(patientDTOList, HttpStatus.OK);
    }
}