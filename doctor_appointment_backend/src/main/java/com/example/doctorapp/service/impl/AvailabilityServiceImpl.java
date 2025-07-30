package com.example.doctorapp.service.impl;

import com.example.doctorapp.dto.AvailabilityDTO;
import com.example.doctorapp.entity.Availability;
import com.example.doctorapp.entity.Doctor;
import com.example.doctorapp.repository.AvailabilityRepository;
import com.example.doctorapp.repository.DoctorRepository;
import com.example.doctorapp.service.AvailabilityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public AvailabilityDTO addAvailability(Long doctorId, AvailabilityDTO dto) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        Availability availability = new Availability();
        availability.setDay(dto.getDay());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());
        availability.setDoctor(doctor);
        return toDTO(availabilityRepository.save(availability));
    }

    @Override
    public List<AvailabilityDTO> getDoctorAvailability(Long doctorId) {
        return availabilityRepository.findByDoctorId(doctorId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private AvailabilityDTO toDTO(Availability availability) {
        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setId(availability.getId());
        dto.setDay(availability.getDay());
        dto.setStartTime(availability.getStartTime());
        dto.setEndTime(availability.getEndTime());
        dto.setDoctorId(availability.getDoctor().getId());
        return dto;
    }
}
