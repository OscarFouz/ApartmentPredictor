package com.example.apartment_predictor.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DUPLEX")
public class Duplex extends Apartment {

    private String balcony;
    private boolean elevator;
    private boolean hasSeparateUtilities;

    public Duplex() {
    }

    public Duplex(String balcony, boolean elevator, boolean hasSeparateUtilities) {
        this.balcony = balcony;
        this.elevator = elevator;
        this.hasSeparateUtilities = hasSeparateUtilities;
    }

    @Override
    public double calculatePrice() {
        double basePrice = area * 120 + (bedrooms * 8000);
        if (elevator) {
            basePrice *= 1.15;
        }
        if (hasSeparateUtilities) {
            basePrice *= 1.15;
        }
        return basePrice * (1 + (locationRating * 0.04));
    }

    public String getBalcony() {
        return balcony;
    }

    public void setBalcony(String balcony) {
        this.balcony = balcony;
    }

    public boolean isElevator() {
        return elevator;
    }

    public void setElevator(boolean elevator) {
        this.elevator = elevator;
    }

    public boolean isHasSeparateUtilities() {
        return hasSeparateUtilities;
    }

    public void setHasSeparateUtilities(boolean hasSeparateUtilities) {
        this.hasSeparateUtilities = hasSeparateUtilities;
    }

    @Override
    public String toString() {
        return "Duplex{" +
                "id='" + id + '\'' +
                ", balcony='" + balcony + '\'' +
                ", elevator=" + elevator +
                ", hasSeparateUtilities=" + hasSeparateUtilities +
                '}';
    }
}
