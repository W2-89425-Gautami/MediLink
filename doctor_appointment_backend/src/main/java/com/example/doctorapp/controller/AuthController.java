package com.example.doctorapp.controller;

import com.example.doctorapp.dto.AuthRequest;
import com.example.doctorapp.dto.AuthResponse;
import com.example.doctorapp.repository.UserRepository;
import com.example.doctorapp.security.JwtTokenHelper;
import com.example.doctorapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest request) {
        try {
            // Check if user already exists
            if (userRepository.existsByEmail(request.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponse(null, null, "Email already registered"));
            }
            
            // Register the user
            userService.registerUser(request.getEmail(), request.getPassword());
            
            // Generate token
            String token = jwtTokenHelper.generateToken(request.getEmail());
            
            return ResponseEntity.ok(new AuthResponse(token, request.getEmail(), "User registered successfully"));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse(null, null, "Registration failed: " + e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        try {
            // Authenticate user credentials
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            
            // Generate token after successful authentication
            String token = jwtTokenHelper.generateToken(request.getEmail());
            
            return ResponseEntity.ok(new AuthResponse(token, request.getEmail(), "Login successful"));
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, null, "Invalid email or password"));
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, null, "User account is disabled"));
        } catch (LockedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, null, "User account is locked"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse(null, null, "Authentication failed: " + e.getMessage()));
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new AuthResponse(null, null, "Invalid token format"));
            }
            
            String token = authHeader.substring(7);
            String email = jwtTokenHelper.getUsernameFromToken(token);
            
            if (email != null && !jwtTokenHelper.isTokenExpired(token)) {
                String newToken = jwtTokenHelper.generateToken(email);
                return ResponseEntity.ok(new AuthResponse(newToken, email, "Token refreshed successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new AuthResponse(null, null, "Invalid or expired token"));
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse(null, null, "Token refresh failed: " + e.getMessage()));
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(@RequestHeader("Authorization") String authHeader) {
        // In a stateless JWT setup, logout is handled client-side by removing the token
        // You could implement token blacklisting here if needed
        return ResponseEntity.ok(new AuthResponse(null, null, "Logged out successfully"));
    }
    
    @GetMapping("/validate")
    public ResponseEntity<AuthResponse> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new AuthResponse(null, null, "Invalid token format"));
            }
            
            String token = authHeader.substring(7);
            String email = jwtTokenHelper.getUsernameFromToken(token);
            
            if (email != null && !jwtTokenHelper.isTokenExpired(token)) {
                return ResponseEntity.ok(new AuthResponse(token, email, "Token is valid"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new AuthResponse(null, null, "Invalid or expired token"));
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, null, "Token validation failed"));
        }
    }
}