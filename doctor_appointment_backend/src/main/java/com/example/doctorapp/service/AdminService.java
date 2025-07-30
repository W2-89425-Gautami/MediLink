package com.example.doctorapp.service;

import com.example.doctorapp.dto.DoctorDTO;
import com.example.doctorapp.dto.UserDTO;

import java.util.List;

public interface AdminService {
    List<UserDTO> getAllUsers();
    List<DoctorDTO> getAllDoctors();
    void enableDoctor(Long doctorId);
    void disableDoctor(Long doctorId);
    void deleteUser(Long userId);
}