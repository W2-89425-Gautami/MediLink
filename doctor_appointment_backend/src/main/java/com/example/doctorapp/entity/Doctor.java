package com.example.doctorapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String specialization;

    private String qualification;

    private String phone;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
