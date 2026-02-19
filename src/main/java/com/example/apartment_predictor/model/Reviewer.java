package com.example.apartment_predictor.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Reviewer extends Person {

    private String email;
    private int reputation;

    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public Reviewer() {
    }

    public Reviewer(String name, String email, int reputation) {
        super(name);
        this.email = email;
        this.reputation = reputation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setReviewer(this);
    }

    @Override
    public String toString() {
        return "Reviewer{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + email + '\'' +
                ", reputation=" + reputation +
                '}';
    }
}
