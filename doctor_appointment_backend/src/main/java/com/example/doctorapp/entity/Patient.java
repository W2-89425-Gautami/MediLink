package com.example.doctorapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;

    private String gender;

    private String address;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
