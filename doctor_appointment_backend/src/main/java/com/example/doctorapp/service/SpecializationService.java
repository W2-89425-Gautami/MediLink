package com.example.doctorapp.service;

import com.example.doctorapp.dto.SpecializationDTO;
import java.util.List;

public interface SpecializationService {
    SpecializationDTO createSpecialization(SpecializationDTO specializationDto);
    SpecializationDTO getSpecializationById(Long id);
    List<SpecializationDTO> getAllSpecializations();
    SpecializationDTO updateSpecialization(Long id, SpecializationDTO specializationDto);
    void deleteSpecialization(Long id);
}
