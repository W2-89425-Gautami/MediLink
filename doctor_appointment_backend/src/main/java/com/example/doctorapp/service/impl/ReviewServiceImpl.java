package com.example.doctorapp.service.impl;

import com.example.doctorapp.dto.ReviewDTO;
import com.example.doctorapp.entity.Doctor;
import com.example.doctorapp.entity.Patient;
import com.example.doctorapp.entity.Review;
import com.example.doctorapp.exception.ResourceNotFoundException;
import com.example.doctorapp.repository.DoctorRepository;
import com.example.doctorapp.repository.PatientRepository;
import com.example.doctorapp.repository.ReviewRepository;
import com.example.doctorapp.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDto) {
        Doctor doctor = doctorRepository.findById(reviewDto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + reviewDto.getDoctorId()));

        Patient patient = patientRepository.findById(reviewDto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + reviewDto.getPatientId()));

        Review review = new Review();
        review.setDoctor(doctor);
        review.setPatient(patient);
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());

        Review saved = reviewRepository.save(review);

        return mapToDTO(saved);
    }

    @Override
    public ReviewDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        return mapToDTO(review);
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));

        if (reviewDto.getDoctorId() != null) {
            Doctor doctor = doctorRepository.findById(reviewDto.getDoctorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + reviewDto.getDoctorId()));
            review.setDoctor(doctor);
        }

        if (reviewDto.getPatientId() != null) {
            Patient patient = patientRepository.findById(reviewDto.getPatientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + reviewDto.getPatientId()));
            review.setPatient(patient);
        }

        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());

        Review updated = reviewRepository.save(review);
        return mapToDTO(updated);
    }

    @Override
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        reviewRepository.delete(review);
    }

    // Mapping Method
    private ReviewDTO mapToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setDoctorId(review.getDoctor().getId());
        dto.setPatientId(review.getPatient().getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        return dto;
    }
}
