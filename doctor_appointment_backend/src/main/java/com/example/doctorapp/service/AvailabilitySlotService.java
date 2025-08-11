package com.example.doctorapp.service;

import com.example.doctorapp.dto.AvailabilitySlotDTO;
import java.util.List;

public interface AvailabilitySlotService {
    AvailabilitySlotDTO createSlot(AvailabilitySlotDTO slotDto);
    AvailabilitySlotDTO getSlotById(Long id);
    List<AvailabilitySlotDTO> getAllSlots();
    AvailabilitySlotDTO updateSlot(Long id, AvailabilitySlotDTO slotDto);
    void deleteSlot(Long id);
}
