package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Reviewer;
import com.example.apartment_predictor.repository.ReviewerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewerService {

    private final ReviewerRepository reviewerRepository;

    // ============================
    // CONSTRUCTOR
    // ============================
    public ReviewerService(ReviewerRepository reviewerRepository) {
        this.reviewerRepository = reviewerRepository;
    }

    // ============================
    // FIND ALL
    // ============================
    public Iterable<Reviewer> findAll() {
        return reviewerRepository.findAll();
    }

    // ============================
    // UPDATE DIRECTO
    // ============================
    public Reviewer updateReviewer(Reviewer reviewer){
        return reviewerRepository.save(reviewer);
    }

    // ============================
    // UPDATE POR ID
    // ============================
    public Reviewer updateReviewerById(String id, Reviewer newReviewer) {
        Optional<Reviewer> existingOpt = reviewerRepository.findById(id);
        if (existingOpt.isEmpty()) return null;
        return reviewerRepository.save(newReviewer);
    }

    // ============================
    // DELETE
    // ============================
    public void deleteReviewer(String id){
        reviewerRepository.deleteById(id);
    }

    // ============================
    // FIND BY ID
    // ============================
    public Reviewer findReviewerById(String id){
        return reviewerRepository.findById(id).orElse(null);
    }
}
