package com.example.doctorapp.controller;

import com.example.doctorapp.dto.SpecializationDTO;
import com.example.doctorapp.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specializations")
public class SpecializationController {

    @Autowired
    private SpecializationService specializationService;

    @GetMapping
    public List<SpecializationDTO> getAllSpecializations() {
        return specializationService.getAllSpecializations();
    }

    @GetMapping("/{id}")
    public SpecializationDTO getById(@PathVariable Long id) {
        return specializationService.getSpecializationById(id);
    }

    @PostMapping
    public SpecializationDTO create(@RequestBody SpecializationDTO dto) {
        return specializationService.createSpecialization(dto);
    }

    @PutMapping("/{id}")
    public SpecializationDTO update(@PathVariable Long id, @RequestBody SpecializationDTO dto) {
        return specializationService.updateSpecialization(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        specializationService.deleteSpecialization(id);
    }
}
