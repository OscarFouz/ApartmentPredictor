package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.Property;
import com.example.apartment_predictor.model.Review;
import com.example.apartment_predictor.repository.ReviewRepository;
import com.example.apartment_predictor.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PropertyService propertyService;

    // ============================
    // GET REVIEWS BY PROPERTY
    // ============================
    @GetMapping("/property/{id}")
    public ResponseEntity<?> getReviewsByProperty(@PathVariable String id) {

        Property property = propertyService.findById(id);

        if (property == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(property.getReviews());
    }

    // ============================
    // CREATE REVIEW
    // ============================
    @PostMapping("/property/{id}")
    public ResponseEntity<Review> addReview(
            @PathVariable String id,
            @RequestBody Review review) {

        Property property = propertyService.findById(id);

        if (property == null) {
            return ResponseEntity.notFound().build();
        }

        review.setProperty(property);
        Review saved = reviewRepository.save(review);

        return ResponseEntity
                .created(URI.create("/api/reviews/" + saved.getId()))
                .body(saved);
    }

    // ============================
    // DELETE REVIEW
    // ============================
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable String reviewId) {

        if (!reviewRepository.existsById(reviewId)) {
            return ResponseEntity.notFound().build();
        }

        reviewRepository.deleteById(reviewId);
        return ResponseEntity.noContent().build();
    }
}
