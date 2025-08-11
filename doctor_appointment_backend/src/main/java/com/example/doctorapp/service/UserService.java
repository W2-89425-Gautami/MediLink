package com.example.doctorapp.service;

import com.example.doctorapp.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDto);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserDTO userDto);
    void deleteUser(Long id);
	UserDTO registerUser(String email, String password);
}
