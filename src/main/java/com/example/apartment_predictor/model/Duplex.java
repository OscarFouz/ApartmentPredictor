package com.example.apartment_predictor.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Duplex {

    @Id
    private String id = UUID.randomUUID().toString();

    private String balcony;
    private boolean elevator;
    private boolean hasSeparateUtilities;

    @OneToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    public Duplex() {}

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

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    @Override
    public String toString() {
        return "Duplex{" +
                "id='" + id + '\'' +
                ", balcony='" + balcony + '\'' +
                ", elevator=" + elevator +
                ", hasSeparateUtilities=" + hasSeparateUtilities +
                ", apartmentId='" + (apartment != null ? apartment.getId() : null) + '\'' +
                '}';
    }
}
