package com.example.apartment_predictor.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class House {

    @Id
    private String id = UUID.randomUUID().toString();

    private int garageQty;
    private String roofType;
    private String garden;

    @OneToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    public House() {}

    public int getGarageQty() {
        return garageQty;
    }

    public void setGarageQty(int garageQty) {
        this.garageQty = garageQty;
    }

    public String getRoofType() {
        return roofType;
    }

    public void setRoofType(String roofType) {
        this.roofType = roofType;
    }

    public String getGarden() {
        return garden;
    }

    public void setGarden(String garden) {
        this.garden = garden;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    @Override
    public String toString() {
        return "House{" +
                "id='" + id + '\'' +
                ", garageQty=" + garageQty +
                ", roofType='" + roofType + '\'' +
                ", garden='" + garden + '\'' +
                ", apartmentId='" + (apartment != null ? apartment.getId() : null) + '\'' +
                '}';
    }
}
