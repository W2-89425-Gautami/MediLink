package com.example.doctorapp.service;

import com.example.doctorapp.dto.DoctorDTO;
import java.util.List;

public interface DoctorService {
	DoctorDTO createDoctor(DoctorDTO doctorDto);
    DoctorDTO getDoctorById(Long id);
    List<DoctorDTO> getAllDoctors();
    DoctorDTO updateDoctor(Long id, DoctorDTO doctorDto);
    void deleteDoctor(Long id);
}
