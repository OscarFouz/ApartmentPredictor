package com.example.apartment_predictor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    @Lob
    private String content;

    private int rating;
    private LocalDate reviewDate;

    @ManyToOne
    @JoinColumn(name = "property_id")
    @JsonBackReference("property-reviews")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    @JsonBackReference("reviewer-reviews")
    private Reviewer reviewer;

    public Review() {
    }

    public Review(String title, String content, int rating,
                  LocalDate reviewDate, Property property, Reviewer reviewer) {
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.reviewDate = reviewDate;
        this.property = property;
        this.reviewer = reviewer;
    }
}
