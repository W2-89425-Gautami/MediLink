package com.example.doctorapp.dto;

import com.example.doctorapp.enums.Role;

//import com.example.doctorapp.dto.Role;

//import com.example.doctorapp.entity.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class AuthResponse {

    private String token;
    private String username;
    private String message;
    private long id;
    
    @Enumerated(EnumType.STRING)
    private Role role;
   

    public AuthResponse() {}
    
    public AuthResponse(String token, String username, String message) {
        this.token = token;
        this.username = username;
        this.message = message;
    }

	// Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
