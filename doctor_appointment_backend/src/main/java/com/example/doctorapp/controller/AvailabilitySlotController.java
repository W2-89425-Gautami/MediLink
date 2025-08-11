package com.example.doctorapp.controller;

import com.example.doctorapp.dto.AvailabilitySlotDTO;
import com.example.doctorapp.service.AvailabilitySlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
public class AvailabilitySlotController {

    @Autowired
    private AvailabilitySlotService slotService;

    @GetMapping
    public List<AvailabilitySlotDTO> getAllSlots() {
        return slotService.getAllSlots();
    }

    @GetMapping("/{id}")
    public AvailabilitySlotDTO getSlotById(@PathVariable Long id) {
        return slotService.getSlotById(id);
    }

    @PostMapping
    public AvailabilitySlotDTO createSlot(@RequestBody AvailabilitySlotDTO dto) {
        return slotService.createSlot(dto);
    }

    @PutMapping("/{id}")
    public AvailabilitySlotDTO updateSlot(@PathVariable Long id, @RequestBody AvailabilitySlotDTO dto) {
        return slotService.updateSlot(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteSlot(@PathVariable Long id) {
        slotService.deleteSlot(id);
    }
}
