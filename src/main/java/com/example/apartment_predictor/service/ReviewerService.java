package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Reviewer;
import com.example.apartment_predictor.repository.ReviewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewerService {

    @Autowired
    ReviewerRepository townReviewerRepository;

    public Iterable<Reviewer> findAll() {
        return townReviewerRepository.findAll();
    }


    public Reviewer updateReviewer(Reviewer townReviewer){
        return townReviewerRepository.save(townReviewer);
    }

    /**
     * PUT REAL: Reemplaza completamente el townReviewer existente por el nuevo.
     */
    public Reviewer updateReviewerById(String id, Reviewer newReviewer) {

        Optional<Reviewer> existingOpt = townReviewerRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return null;
        }

        Reviewer existing = existingOpt.get();

        return townReviewerRepository.save(newReviewer);
    }

    public void deleteReviewer(String id){
        townReviewerRepository.deleteById(id);
    }

    public Reviewer findReviewerById(String id){
        return townReviewerRepository.findById(id).orElse(null);
    }



}