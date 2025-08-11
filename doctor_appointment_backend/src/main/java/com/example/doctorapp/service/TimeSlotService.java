package com.example.doctorapp.service;

import com.example.doctorapp.dto.TimeSlotDTO;

import java.util.List;

public interface TimeSlotService {
    TimeSlotDTO createTimeSlot(TimeSlotDTO timeSlotDto);
    TimeSlotDTO getTimeSlotById(Long id);
    List<TimeSlotDTO> getAllTimeSlots();
    List<TimeSlotDTO> getTimeSlotsByAvailabilitySlotId(Long availabilitySlotId);
    TimeSlotDTO updateTimeSlot(Long id, TimeSlotDTO timeSlotDto);
    void deleteTimeSlot(Long id);
}