package com.example.apartment_predictor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;
    private String content;
    private int rating;
    private LocalDate reviewDate;

    @ManyToOne
    @JoinColumn(name = "apartment_fk")
    @JsonBackReference
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private Reviewer reviewer;

    public Review() {
    }

    public Review(String title, String content, int rating, LocalDate reviewDate, Apartment apartment, Reviewer reviewer) {
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.reviewDate = reviewDate;
        this.apartment = apartment;
        this.reviewer = reviewer;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", reviewDate=" + reviewDate +
                '}';
    }
}
