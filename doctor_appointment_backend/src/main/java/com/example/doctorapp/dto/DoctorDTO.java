package com.example.doctorapp.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDTO {
    private Long id;
    private String name;
    private String specialization;
    private String email;
}
