package com.example.doctorapp.controller;

import com.example.doctorapp.dto.ReviewDto;
import com.example.doctorapp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDto> addReview(@RequestBody ReviewDto dto) {
        return ResponseEntity.ok(reviewService.addReview(dto));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(reviewService.getReviewsByDoctor(doctorId));
    }
}
