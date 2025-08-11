package com.example.doctorapp.mapper;

import com.example.doctorapp.dto.UserDTO;
import com.example.doctorapp.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        return dto;
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.getId());
        user.setUserName(dto.getUsername());
        user.setEmail(dto.getEmail());
        // Role should be set externally in service by fetching Role from RoleRepository
        return user;
    }
}
