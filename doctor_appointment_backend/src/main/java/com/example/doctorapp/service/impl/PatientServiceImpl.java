package com.example.doctorapp.service.impl;

import com.example.doctorapp.dto.PatientDTO;
import com.example.doctorapp.entity.Patient;
import com.example.doctorapp.repository.PatientRepository;
import com.example.doctorapp.service.PatientService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public PatientDTO addPatient(PatientDTO dto) {
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setAge(dto.getAge());
        return toDTO(patientRepository.save(patient));
    }

    @Override
    public PatientDTO getPatientById(Long id) {
        return patientRepository.findById(id).map(this::toDTO).orElse(null);
    }

    private PatientDTO toDTO(Patient patient) {
        PatientDTO dto = new PatientDTO();
        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setAge(patient.getAge());
        return dto;
    }
}
