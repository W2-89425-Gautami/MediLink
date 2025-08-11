package com.example.doctorapp.service.impl;

import com.example.doctorapp.dto.AuthRequest;
import com.example.doctorapp.dto.AuthResponse;
import com.example.doctorapp.entity.User;
import com.example.doctorapp.repository.UserRepository;
import com.example.doctorapp.security.JwtTokenHelper;
import com.example.doctorapp.service.AuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager = null;
    private final UserRepository userRepository = null;
    private final JwtTokenHelper jwtTokenHelper = new JwtTokenHelper();

    @Override
    public AuthResponse login(AuthRequest request) {
        // Authenticate the user
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // Get user details
        String userDetails = (String) authentication.getPrincipal();
        String token = jwtTokenHelper.generateToken(userDetails);

        // Fetch user from DB
        User user = userRepository.findByUserName(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Build response
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setId(user.getId());
        response.setUsername(user.getUserName());
        response.setRole(user.getRole());

        return response;
    }

	
}
