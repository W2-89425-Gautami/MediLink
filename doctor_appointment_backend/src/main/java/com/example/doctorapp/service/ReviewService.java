package com.example.doctorapp.service;

import com.example.doctorapp.dto.ReviewDTO;
import java.util.List;

public interface ReviewService {
    ReviewDTO addReview(Long doctorId, ReviewDTO reviewDTO);
    List<ReviewDTO> getReviewsForDoctor(Long doctorId);
}
