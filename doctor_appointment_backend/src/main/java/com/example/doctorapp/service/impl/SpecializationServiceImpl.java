package com.example.doctorapp.service.impl;

import com.example.doctorapp.dto.SpecializationDTO;
import com.example.doctorapp.entity.Specialization;
import com.example.doctorapp.exception.ResourceNotFoundException;
import com.example.doctorapp.repository.SpecializationRepository;
import com.example.doctorapp.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecializationServiceImpl implements SpecializationService {

    @Autowired
    private SpecializationRepository specializationRepository;

    private SpecializationDTO mapToDTO(Specialization specialization) {
        return new SpecializationDTO(
                specialization.getId(),
                specialization.getName(),
                specialization.getDescription()
        );
    }

    private Specialization mapToEntity(SpecializationDTO dto) {
        Specialization specialization = new Specialization();
        specialization.setId(dto.getId());
        specialization.setName(dto.getName());
        specialization.setDescription(dto.getDescription());
        return specialization;
    }

    @Override
    public SpecializationDTO createSpecialization(SpecializationDTO dto) {
        Specialization specialization = mapToEntity(dto);
        return mapToDTO(specializationRepository.save(specialization));
    }

    @Override
    public SpecializationDTO getSpecializationById(Long id) {
        Specialization specialization = specializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialization", "id", id));
        return mapToDTO(specialization);
    }

    @Override
    public List<SpecializationDTO> getAllSpecializations() {
        return specializationRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SpecializationDTO updateSpecialization(Long id, SpecializationDTO dto) {
        Specialization specialization = specializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialization", "id", id));

        specialization.setName(dto.getName());
        specialization.setDescription(dto.getDescription());

        return mapToDTO(specializationRepository.save(specialization));
    }

    @Override
    public void deleteSpecialization(Long id) {
        Specialization specialization = specializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialization", "id", id));
        specializationRepository.delete(specialization);
    }
}
