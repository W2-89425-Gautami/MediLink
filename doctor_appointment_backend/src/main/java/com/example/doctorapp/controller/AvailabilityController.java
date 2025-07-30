package com.example.doctorapp.controller;

import com.example.doctorapp.dto.AvailabilityDTO;
import com.example.doctorapp.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PostMapping
    public ResponseEntity<AvailabilityDTO> addAvailability(@RequestBody AvailabilityDTO dto) {
        return ResponseEntity.ok(availabilityService.createAvailability(dto));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AvailabilityDTO>> getDoctorAvailability(@PathVariable Long doctorId) {
        return ResponseEntity.ok(availabilityService.getDoctorAvailability(doctorId));
    }
}
