package com.example.doctorapp.service.impl;

import com.example.doctorapp.dto.ReviewDTO;
import com.example.doctorapp.entity.Doctor;
import com.example.doctorapp.entity.Review;
import com.example.doctorapp.entity.User;
import com.example.doctorapp.repository.DoctorRepository;
import com.example.doctorapp.repository.ReviewRepository;
import com.example.doctorapp.repository.UserRepository;
import com.example.doctorapp.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    @Override
    public ReviewDTO addReview(Long doctorId, Long userId, ReviewDTO dto) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        Review review = new Review();
        review.setDoctor(doctor);
        review.setUser(user);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        return toDTO(reviewRepository.save(review));
    }

    @Override
    public List<ReviewDTO> getReviewsForDoctor(Long doctorId) {
        return reviewRepository.findByDoctorId(doctorId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setDoctorId(review.getDoctor().getId());
        dto.setUserId(review.getUser().getId());
        return dto;
    }
}
