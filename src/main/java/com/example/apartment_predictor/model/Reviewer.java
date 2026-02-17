
package com.example.apartment_predictor.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("REVIEWER")
public class Reviewer extends Person {

    private String email;
    private int reputation;

    public Reviewer() {}

    // getters y setters

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

    @Override
    public String toString() {
        return "Reviewer{" +
                "email='" + email + '\'' +
                ", reputation=" + reputation +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
