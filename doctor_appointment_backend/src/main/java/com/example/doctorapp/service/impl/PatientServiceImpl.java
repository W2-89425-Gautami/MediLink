package com.example.doctorapp.service.impl;

import com.example.doctorapp.dto.PatientDTO;
import com.example.doctorapp.entity.Patient;
import com.example.doctorapp.entity.User;
import com.example.doctorapp.exception.ResourceNotFoundException;
import com.example.doctorapp.repository.PatientRepository;
import com.example.doctorapp.repository.UserRepository;
import com.example.doctorapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = mapToEntity(patientDTO);
        Patient saved = patientRepository.save(patient);
        return mapToDTO(saved);
    }

    @Override
    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "ID", id));
        return mapToDTO(patient);
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        Patient existing = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "ID", id));

        existing.setFullName(patientDTO.getFullName());
        existing.setAge(patientDTO.getAge());
        existing.setGender(patientDTO.getGender());

        if (patientDTO.getUserId() != null) {
            User user = userRepository.findById(patientDTO.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "ID", patientDTO.getUserId()));
            existing.setUser(user);
        }

        Patient updated = patientRepository.save(existing);
        return mapToDTO(updated);
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "ID", id));
        patientRepository.delete(patient);
    }

    // Entity to DTO mapping
    private PatientDTO mapToDTO(Patient patient) {
        if (patient == null) return null;

        PatientDTO dto = new PatientDTO();
        dto.setId(patient.getId());
        dto.setFullName(patient.getFullName());
        dto.setAge(patient.getAge());
        dto.setGender(patient.getGender());
        dto.setUserId(patient.getUser() != null ? patient.getUser().getId() : null);
        return dto;
    }

    // DTO to Entity mapping
    private Patient mapToEntity(PatientDTO dto) {
        if (dto == null) return null;

        Patient patient = new Patient();
        patient.setId(dto.getId());
        patient.setFullName(dto.getFullName());
        patient.setAge(dto.getAge());
        patient.setGender(dto.getGender());

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "ID", dto.getUserId()));
            patient.setUser(user);
        }

        return patient;
    }
}
