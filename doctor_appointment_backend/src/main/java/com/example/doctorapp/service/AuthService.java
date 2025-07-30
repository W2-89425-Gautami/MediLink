package com.example.doctorapp.service;

import com.example.doctorapp.dto.AuthRequest;
import com.example.doctorapp.dto.AuthResponse;
import com.example.doctorapp.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(AuthRequest request);
}
