package com.example.doctorapp.service;

import com.example.doctorapp.dto.DoctorDTO;
import java.util.List;

public interface DoctorService {
    DoctorDTO getDoctorById(Long id);
    List<DoctorDTO> getAllDoctors();
    DoctorDTO updateDoctor(Long id, DoctorDTO doctorDTO);
    void deleteDoctor(Long id);
}
