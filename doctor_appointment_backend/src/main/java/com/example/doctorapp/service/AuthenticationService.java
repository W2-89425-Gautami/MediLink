package com.example.doctorapp.service;

import com.example.doctorapp.dto.AuthRequest;
import com.example.doctorapp.dto.AuthResponse;

public interface AuthenticationService {
    AuthResponse login(AuthRequest request);
}
