package com.example.doctorapp.controller;

import com.example.doctorapp.dto.DoctorDTO;
import com.example.doctorapp.dto.UserDTO;
import com.example.doctorapp.service.AdminService;
//import com.example.doctorapp.util.ResponseUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> users = adminService.getAllUsers();
        return ResponseUtil.success("All users fetched successfully", users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/doctors")
    public ResponseEntity<?> getAllDoctors() {
        List<DoctorDTO> doctors = adminService.getAllDoctors();
        return ResponseUtil.success("All doctors fetched successfully", doctors);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/doctor/{id}/enable")
    public ResponseEntity<?> enableDoctor(@PathVariable Long id) {
        adminService.enableDoctor(id);
        return ResponseUtil.success("Doctor enabled successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/doctor/{id}/disable")
    public ResponseEntity<?> disableDoctor(@PathVariable Long id) {
        adminService.disableDoctor(id);
        return ResponseUtil.success("Doctor disabled successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseUtil.success("User deleted successfully");
    }
}