package com.example.doctorapp.service.impl;

import com.example.doctorapp.dto.AvailabilitySlotDTO;
import com.example.doctorapp.entity.AvailabilitySlot;
import com.example.doctorapp.entity.Doctor;
import com.example.doctorapp.exception.ResourceNotFoundException;
import com.example.doctorapp.repository.AvailabilitySlotRepository;
import com.example.doctorapp.repository.DoctorRepository;
import com.example.doctorapp.service.AvailabilitySlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilitySlotServiceImpl implements AvailabilitySlotService {

    @Autowired
    private AvailabilitySlotRepository availabilitySlotRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public AvailabilitySlotDTO createSlot(AvailabilitySlotDTO slotDto) {
        Doctor doctor = doctorRepository.findById(slotDto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", slotDto.getDoctorId()));

        AvailabilitySlot slot = new AvailabilitySlot();
        slot.setDayOfWeek(slotDto.getDayOfWeek());
        slot.setStartTime(slotDto.getStartTime());
        slot.setEndTime(slotDto.getEndTime());
        slot.setIsBooked(false);
        slot.setDoctor(doctor);

        AvailabilitySlot savedSlot = availabilitySlotRepository.save(slot);
        return mapToDTO(savedSlot);
    }

    @Override
    public AvailabilitySlotDTO getSlotById(Long id) {
        AvailabilitySlot slot = availabilitySlotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AvailabilitySlot", "id", id));
        return mapToDTO(slot);
    }

    @Override
    public List<AvailabilitySlotDTO> getAllSlots() {
        List<AvailabilitySlot> slots = availabilitySlotRepository.findAll();
        return slots.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public AvailabilitySlotDTO updateSlot(Long id, AvailabilitySlotDTO slotDto) {
        AvailabilitySlot slot = availabilitySlotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AvailabilitySlot", "id", id));

        slot.setDayOfWeek(slotDto.getDayOfWeek());
        slot.setStartTime(slotDto.getStartTime());
        slot.setEndTime(slotDto.getEndTime());
        slot.setIsBooked(slotDto.getIsBooked());

        AvailabilitySlot updatedSlot = availabilitySlotRepository.save(slot);
        return mapToDTO(updatedSlot);
    }

    @Override
    public void deleteSlot(Long id) {
        AvailabilitySlot slot = availabilitySlotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AvailabilitySlot", "id", id));
        availabilitySlotRepository.delete(slot);
    }

    // Helper method
    private AvailabilitySlotDTO mapToDTO(AvailabilitySlot slot) {
        AvailabilitySlotDTO dto = new AvailabilitySlotDTO();
        dto.setId(slot.getId());
        dto.setDayOfWeek(slot.getDayOfWeek());
        dto.setStartTime(slot.getStartTime());
        dto.setEndTime(slot.getEndTime());
        dto.setIsBooked(slot.getIsBooked());
        dto.setDoctorId(slot.getDoctor().getId());
        return dto;
    }
}
