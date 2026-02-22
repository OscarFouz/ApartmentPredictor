package com.example.apartment_predictor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Reviewer extends Person {

    // ============================
    // CAMPOS
    // ============================
    private int reputation;
    private boolean isBusiness;
    private String xAccount;
    private String webURL;
    private int qtyReviews;

    // ============================
    // RELACIONES
    // ============================
    @OneToMany(mappedBy = "reviewer")
    private List<Review> reviews = new ArrayList<>();

    // ============================
    // CONSTRUCTORES
    // ============================
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

    // ============================
    // GETTERS
    // ============================
    public int getReputation() {return reputation;}
    public boolean isBusiness() {return isBusiness;}
    public String getxAccount() {return xAccount;}
    public String getWebURL() {return webURL;}
    public int getQtyReviews() {return qtyReviews;}
    public List<Review> getReviews() {return reviews;}

    // ============================
    // SETTERS
    // ============================
    public void setReputation(int reputation) {this.reputation = reputation;}
    public void setBusiness(boolean business) {isBusiness = business;}
    public void setxAccount(String xAccount) {this.xAccount = xAccount;}
    public void setWebURL(String webURL) {this.webURL = webURL;}
    public void setQtyReviews(int qtyReviews) {this.qtyReviews = qtyReviews;}
    public void setReviews(List<Review> reviews) {this.reviews = reviews;}

    // ============================
    // TO STRING
    // ============================
    @Override
    public String toString() {
        return "Reviewer{" +
                "id='" + getId() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", reputation=" + reputation +
                ", isBusiness=" + isBusiness +
                ", xAccount='" + xAccount + '\'' +
                ", webURL='" + webURL + '\'' +
                ", qtyReviews=" + qtyReviews +
                '}';
    }
}
