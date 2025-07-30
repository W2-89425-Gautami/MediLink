package com.example.doctorapp.service;

import com.example.doctorapp.dto.PatientDTO;
import java.util.List;

public interface PatientService {
    PatientDTO getPatientById(Long id);
    List<PatientDTO> getAllPatients();
    PatientDTO updatePatient(Long id, PatientDTO patientDTO);
    void deletePatient(Long id);
}
