package com.example.doctorapp.service;

import com.example.doctorapp.dto.AvailabilityDTO;

import java.util.List;

public interface AvailabilityService {
    AvailabilityDTO createAvailability(AvailabilityDTO availabilityDTO);
    List<AvailabilityDTO> getAllByDoctorId(Long doctorId);
    void deleteAvailability(Long id);
}
