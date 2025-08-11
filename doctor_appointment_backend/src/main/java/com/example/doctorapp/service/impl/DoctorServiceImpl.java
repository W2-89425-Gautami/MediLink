package com.example.doctorapp.service.impl;

import com.example.doctorapp.dto.DoctorDTO;
import com.example.doctorapp.entity.Doctor;
import com.example.doctorapp.entity.Specialization;
import com.example.doctorapp.exception.ResourceNotFoundException;
import com.example.doctorapp.repository.DoctorRepository;
import com.example.doctorapp.repository.SpecializationRepository;
import com.example.doctorapp.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Override
    public DoctorDTO createDoctor(DoctorDTO doctorDto) {
        Doctor doctor = new Doctor();
        doctor.setName(doctorDto.getFullName());
        doctor.setEmail(doctorDto.getEmail());
        doctor.setPhone(doctorDto.getPhoneNumber());

        Specialization specialization = specializationRepository.findById(doctorDto.getSpecializationId())
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found with id " + doctorDto.getSpecializationId()));
        doctor.setSpecialization(specialization);

        Doctor saved = doctorRepository.save(doctor);

        return mapToDTO(saved);
    }

    @Override
    public DoctorDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + id));
        return mapToDTO(doctor);
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDTO updateDoctor(Long id, DoctorDTO doctorDto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + id));

        doctor.setName(doctorDto.getFullName());
        doctor.setEmail(doctorDto.getEmail());
        doctor.setPhone(doctorDto.getPhoneNumber());

        Specialization specialization = specializationRepository.findById(doctorDto.getSpecializationId())
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found with id " + doctorDto.getSpecializationId()));
        doctor.setSpecialization(specialization);

        Doctor updated = doctorRepository.save(doctor);
        return mapToDTO(updated);
    }

    @Override
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + id));
        doctorRepository.delete(doctor);
    }

    // Helper method to map Entity to DTO
    private DoctorDTO mapToDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setFullName(doctor.getName());
        dto.setEmail(doctor.getEmail());
        dto.setPhoneNumber(doctor.getPhone());
        dto.setSpecializationId(doctor.getSpecialization().getId());
        return dto;
    }
}
