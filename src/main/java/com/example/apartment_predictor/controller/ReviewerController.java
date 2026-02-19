package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.Reviewer;
import com.example.apartment_predictor.service.ReviewerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviewers")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewerController {

    private final ReviewerService reviewerService;

    public ReviewerController(ReviewerService reviewerService) {
        this.reviewerService = reviewerService;
    }

    @GetMapping
    public Iterable<Reviewer> getAll() {
        return reviewerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reviewer> getById(@PathVariable String id) {
        Reviewer r = reviewerService.findReviewerById(id);
        return r != null ? ResponseEntity.ok(r) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Reviewer create(@RequestBody Reviewer reviewer) {
        return reviewerService.updateReviewer(reviewer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reviewer> update(@PathVariable String id, @RequestBody Reviewer reviewer) {
        Reviewer updated = reviewerService.updateReviewerById(id, reviewer);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        reviewerService.deleteReviewer(id);
        return ResponseEntity.noContent().build();
    }

    // ============================
    // ENDPOINT DE RELACIÓN
    // ============================

    @GetMapping("/{id}/reviews")
    public ResponseEntity<?> getReviewerReviews(@PathVariable String id) {
        Reviewer reviewer = reviewerService.findReviewerById(id);
        return reviewer != null ? ResponseEntity.ok(reviewer.getReviews()) : ResponseEntity.notFound().build();
    }
}
