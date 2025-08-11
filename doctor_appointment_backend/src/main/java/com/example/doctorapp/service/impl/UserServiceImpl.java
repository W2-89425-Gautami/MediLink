package com.example.doctorapp.service.impl;

import com.example.doctorapp.dto.UserDTO;
import com.example.doctorapp.entity.User;
import com.example.doctorapp.enums.Role;
import com.example.doctorapp.exception.ResourceNotFoundException;
import com.example.doctorapp.repository.UserRepository;
import com.example.doctorapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDTO createUser(UserDTO userDto) {
        User user = mapToEntity(userDto);
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }
    
    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return mapToDTO(user);
    }
    
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    
    @Override
    public UserDTO updateUser(Long id, UserDTO userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        
        existingUser.setUserName(userDto.getUsername());
        existingUser.setEmail(userDto.getEmail());
        
        // Only encode password if it's provided
        if (userDto.getPassword() != null && !userDto.getPassword().trim().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        
        existingUser.setRole(userDto.getRole());
        User updatedUser = userRepository.save(existingUser);
        return mapToDTO(updatedUser);
    }
    
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        userRepository.delete(user);
    }
    
    // Helper: Convert Entity to DTO
    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUserName());
        dto.setEmail(user.getEmail());
        // Don't include password in DTO for security
        dto.setRole(user.getRole());
        return dto;
    }
    
    // Helper: Convert DTO to Entity
    private User mapToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUserName(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        return user;
    }
    
    @Override
    public UserDTO registerUser(String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("User with this email already exists");
        }
        
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER); // Use the enum value, not string
        user.setEnabled(true); // Set enabled to true
        
        // Save the user
        User savedUser = userRepository.save(user);
        
        // Map to DTO and return
        UserDTO dto = new UserDTO();
        dto.setId(savedUser.getId());
        dto.setEmail(savedUser.getEmail());
        dto.setRole(savedUser.getRole());
        return dto;
    }
}