package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.Reviewer;
import com.example.apartment_predictor.service.ReviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviewers")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewerController {

    @Autowired
    private ReviewerService reviewerService;

    // ============================
    // GET ALL
    // ============================
    @GetMapping
    public Iterable<Reviewer> getAll() {
        return reviewerService.findAll();
    }

    // ============================
    // GET BY ID
    // ============================
    @GetMapping("/{id}")
    public ResponseEntity<Reviewer> getById(@PathVariable String id) {

        Reviewer r = reviewerService.findReviewerById(id);
        return r != null ? ResponseEntity.ok(r) : ResponseEntity.notFound().build();
    }

    // ============================
    // CREATE
    // ============================
    @PostMapping
    public Reviewer create(@RequestBody Reviewer reviewer) {
        return reviewerService.updateReviewer(reviewer);
    }

    // ============================
    // UPDATE
    // ============================
    @PutMapping("/{id}")
    public ResponseEntity<Reviewer> update(@PathVariable String id, @RequestBody Reviewer reviewer) {

        Reviewer updated = reviewerService.updateReviewerById(id, reviewer);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // ============================
    // DELETE
    // ============================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        reviewerService.deleteReviewer(id);
        return ResponseEntity.noContent().build();
    }

    // ============================
    // GET REVIEWS OF REVIEWER
    // ============================
    @GetMapping("/{id}/reviews")
    public ResponseEntity<?> getReviewerReviews(@PathVariable String id) {

        Reviewer reviewer = reviewerService.findReviewerById(id);
        return reviewer != null ? ResponseEntity.ok(reviewer.getReviews()) : ResponseEntity.notFound().build();
    }
}
