package com.example.apartment_predictor.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Reviewer extends Person {

    private int reputation;
    private boolean isBusiness;
    private String xAccount;
    private String webURL;
    private int qtyReviews;

    @OneToMany(mappedBy = "reviewer")
    @JsonManagedReference("reviewer-reviews")
    private List<Review> reviews = new ArrayList<>();

    public Reviewer() {
        super();
    }

    public Reviewer(String fullName, String email, int reputation) {
        super(fullName, null, null, email, null, true);
        this.reputation = reputation;
    }

    public Reviewer(String fullName, LocalDate birthDate, String phone, String email,
                    String password, boolean isActive, int reputation,
                    boolean isBusiness, String xAccount, String webURL, int qtyReviews) {
        super(fullName, birthDate, phone, email, password, isActive);
        this.reputation = reputation;
        this.isBusiness = isBusiness;
        this.xAccount = xAccount;
        this.webURL = webURL;
        this.qtyReviews = qtyReviews;
    }
}
