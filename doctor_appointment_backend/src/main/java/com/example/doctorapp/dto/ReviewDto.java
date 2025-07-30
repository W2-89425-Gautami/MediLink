package com.example.doctorapp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDto {
    private Long id;
    private Long doctorId;
    private Long userId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}
