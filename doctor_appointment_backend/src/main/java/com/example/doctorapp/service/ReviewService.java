package com.example.doctorapp.service;

import com.example.doctorapp.dto.ReviewDTO;
import com.example.doctorapp.dto.ReviewDTO;
import java.util.List;

public interface ReviewService {
    ReviewDTO createReview(ReviewDTO reviewDto);
    ReviewDTO getReviewById(Long id);
    List<ReviewDTO> getAllReviews();
    ReviewDTO updateReview(Long id, ReviewDTO reviewDto);
    void deleteReview(Long id);
}
