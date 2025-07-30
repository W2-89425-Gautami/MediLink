package com.example.doctorapp.service.impl;


import com.example.doctorapp.dto.DoctorDTO;
import com.example.doctorapp.dto.UserDTO;
import com.example.doctorapp.entity.Doctor;
import com.example.doctorapp.entity.User;
import com.example.doctorapp.exception.ResourceNotFoundException;
import com.example.doctorapp.repository.DoctorRepository;
import com.example.doctorapp.repository.UserRepository;
import com.example.doctorapp.service.AdminService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(DoctorDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void enableDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        doctor.setEnabled(true);
        doctorRepository.save(doctor);
    }

    @Override
    public void disableDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        doctor.setEnabled(false);
        doctorRepository.save(doctor);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(userId);
    }
}