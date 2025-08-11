package com.example.doctorapp.controller;

import com.example.doctorapp.dto.TimeSlotDTO;
import com.example.doctorapp.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timeslots")
public class TimeSlotController {

    @Autowired
    private TimeSlotService timeSlotService;

    @PostMapping
    public ResponseEntity<TimeSlotDTO> createTimeSlot(@RequestBody TimeSlotDTO timeSlotDto) {
        return ResponseEntity.ok(timeSlotService.createTimeSlot(timeSlotDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeSlotDTO> getTimeSlotById(@PathVariable Long id) {
        return ResponseEntity.ok(timeSlotService.getTimeSlotById(id));
    }

    @GetMapping
    public ResponseEntity<List<TimeSlotDTO>> getAllTimeSlots() {
        return ResponseEntity.ok(timeSlotService.getAllTimeSlots());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeSlotDTO> updateTimeSlot(@PathVariable Long id,
                                                      @RequestBody TimeSlotDTO timeSlotDto) {
        return ResponseEntity.ok(timeSlotService.updateTimeSlot(id, timeSlotDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTimeSlot(@PathVariable Long id) {
        timeSlotService.deleteTimeSlot(id);
        return ResponseEntity.ok("Time slot deleted successfully");
    }

    @GetMapping("/availability/{availabilitySlotId}")
    public ResponseEntity<List<TimeSlotDTO>> getTimeSlotsByAvailabilitySlotId(@PathVariable Long availabilitySlotId) {
        return ResponseEntity.ok(timeSlotService.getTimeSlotsByAvailabilitySlotId(availabilitySlotId));
    }
}
