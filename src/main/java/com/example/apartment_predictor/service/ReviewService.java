package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Review;
import com.example.apartment_predictor.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // ============================
    // CONSTRUCTOR
    // ============================
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // ============================
    // CREATE
    // ============================
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    // ============================
    // DELETE
    // ============================
    public void delete(String id) {
        reviewRepository.deleteById(id);
    }
}
