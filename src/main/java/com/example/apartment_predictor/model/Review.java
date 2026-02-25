package com.example.apartment_predictor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Review {

    // ============================
    // ID
    // ============================
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // ============================
    // CAMPOS
    // ============================
    private String title;

    @Lob
    private String content;

    private int rating;
    private LocalDate reviewDate;

    // ============================
    // RELACIONES
    // ============================
    @ManyToOne
    @JoinColumn(name = "property_id")
    @JsonBackReference("property-reviews")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    @JsonBackReference("reviewer-reviews")
    private Reviewer reviewer;

    // ============================
    // CONSTRUCTORES
    // ============================
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

    // ============================
    // GETTERS
    // ============================
    public String getId() {return id;}
    public String getTitle() {return title;}
    public String getContent() {return content;}
    public int getRating() {return rating;}
    public LocalDate getReviewDate() {return reviewDate;}
    public Property getProperty() {return property;}
    public Reviewer getReviewer() {return reviewer;}

    // ============================
    // SETTERS
    // ============================
    public void setTitle(String title) {this.title = title;}
    public void setContent(String content) {this.content = content;}
    public void setRating(int rating) {this.rating = rating;}
    public void setReviewDate(LocalDate reviewDate) {this.reviewDate = reviewDate;}
    public void setProperty(Property property) {this.property = property;}
    public void setReviewer(Reviewer reviewer) {this.reviewer = reviewer;}

    // ============================
    // TO STRING
    // ============================
    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", reviewDate=" + reviewDate +
                ", propertyId=" + (property != null ? property.getId() : "null") +
                ", reviewerId=" + (reviewer != null ? reviewer.getId() : "null") +
                '}';
    }
}
