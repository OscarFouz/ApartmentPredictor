package com.example.apartment_predictor.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class TownHouse {

    @Id
    private String id = UUID.randomUUID().toString();

    private boolean hasHomeownersAssociation;
    private double hoaMonthlyFee;

    @OneToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    public TownHouse() {}

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

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    @Override
    public String toString() {
        return "TownHouse{" +
                "id='" + id + '\'' +
                ", hasHomeownersAssociation=" + hasHomeownersAssociation +
                ", hoaMonthlyFee=" + hoaMonthlyFee +
                ", apartmentId='" + (apartment != null ? apartment.getId() : null) + '\'' +
                '}';
    }
}
