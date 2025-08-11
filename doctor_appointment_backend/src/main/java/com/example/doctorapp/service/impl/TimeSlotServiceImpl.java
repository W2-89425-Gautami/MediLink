package com.example.doctorapp.service.impl;

import com.example.doctorapp.dto.TimeSlotDTO;
import com.example.doctorapp.entity.TimeSlot;
import com.example.doctorapp.repository.TimeSlotRepository;
import com.example.doctorapp.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public TimeSlotServiceImpl(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    private TimeSlotDTO mapToDTO(TimeSlot timeSlot) {
        TimeSlotDTO dto = new TimeSlotDTO();
        dto.setId(timeSlot.getId());
        dto.setAvailabilitySlot(timeSlot.getAvailabilitySlot());
        return dto;
    }

    private TimeSlot mapToEntity(TimeSlotDTO dto) {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setId(dto.getId());
        timeSlot.setAvailabilitySlot(dto.getAvailabilitySlot());
        return timeSlot;
    }

    @Override
    public TimeSlotDTO createTimeSlot(TimeSlotDTO timeSlotDto) {
        TimeSlot timeSlot = mapToEntity(timeSlotDto);
        TimeSlot saved = timeSlotRepository.save(timeSlot);
        return mapToDTO(saved);
    }

    @Override
    public TimeSlotDTO getTimeSlotById(Long id) {
        TimeSlot timeSlot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TimeSlot not found with id: " + id));
        return mapToDTO(timeSlot);
    }

    @Override
    public List<TimeSlotDTO> getAllTimeSlots() {
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        return timeSlots.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TimeSlotDTO> getTimeSlotsByAvailabilitySlotId(Long availabilitySlotId) {
        List<TimeSlot> timeSlots = timeSlotRepository.findByAvailabilitySlotId(availabilitySlotId);
        return timeSlots.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public TimeSlotDTO updateTimeSlot(Long id, TimeSlotDTO timeSlotDto) {
        TimeSlot timeSlot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TimeSlot not found with id: " + id));

        timeSlot.setAvailabilitySlot(timeSlotDto.getAvailabilitySlot());

        TimeSlot updated = timeSlotRepository.save(timeSlot);
        return mapToDTO(updated);
    }

    @Override
    public void deleteTimeSlot(Long id) {
        if (!timeSlotRepository.existsById(id)) {
            throw new RuntimeException("TimeSlot not found with id: " + id);
        }
        timeSlotRepository.deleteById(id);
    }
}