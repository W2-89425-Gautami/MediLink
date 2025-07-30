package com.example.doctorapp.dto;

import lombok.Data;

@Data
public class PatientDto {
    private Long id;
    private int age;
    private String gender;
    private String address;
    private Long userId;
    private String userName;
}
