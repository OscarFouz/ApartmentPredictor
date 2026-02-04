package com.example.apartment_predictor.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TOWNHOUSE")
public class TownHouse extends Apartment {

    private boolean hasHomeownersAssociation;
    private double hoaMonthlyFee;

    public TownHouse() {
    }

    public TownHouse(Long price, Integer area, Integer bedrooms, Integer bathrooms,
                     Integer stories, String mainroad, String guestroom, String basement,
                     String hotwaterheating, String airconditioning,
                     Integer parking, String prefarea, String furnishingstatus,
                     boolean hasHomeownersAssociation, double hoaMonthlyFee) {

        super(price, area, bedrooms, bathrooms, stories, mainroad, guestroom, basement,
                hotwaterheating, airconditioning, parking, prefarea, furnishingstatus);
        this.hasHomeownersAssociation = hasHomeownersAssociation;
        this.hoaMonthlyFee = hoaMonthlyFee;
    }

    public boolean isHasHomeownersAssociation() {
        return hasHomeownersAssociation;
    }

    public void setHasHomeownersAssociation(boolean hasHomeownersAssociation) {
        this.hasHomeownersAssociation = hasHomeownersAssociation;
    }

    public double getHoaMonthlyFee() {
        return hoaMonthlyFee;
    }

    public void setHoaMonthlyFee(double hoaMonthlyFee) {
        this.hoaMonthlyFee = hoaMonthlyFee;
    }

    @Override
    public double calculatePrice() {
        double basePrice = area * 220 + (bedrooms * 12000);
        if (hasHomeownersAssociation) {
            basePrice = basePrice - hoaMonthlyFee;
        }
        return basePrice * (1 + (locationRating * 0.09));
    }

    @Override
    public String toString() {
        return "TownHouse{" +
                "hasHomeownersAssociation=" + hasHomeownersAssociation +
                ", hoaMonthlyFee=" + hoaMonthlyFee +
                ", id='" + id + '\'' +
                ", bedrooms=" + bedrooms +
                ", area=" + area +
                ", locationRating=" + locationRating +
                '}';
    }
}
