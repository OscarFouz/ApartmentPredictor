package com.example.apartment_predictor.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("HOUSE")
public class House extends Apartment {

    private int garageQty;
    private String roofType;
    private String garden;

    public House() {
    }

    public House(int garageQty, String roofType, String garden) {
        this.garageQty = garageQty;
        this.roofType = roofType;
        this.garden = garden;
    }

    @Override
    public double calculatePrice() {
        double base = super.calculatePrice();
        return base + (garageQty * 5000);
    }

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

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", garageQty=" + garageQty +
                ", roofType='" + roofType + '\'' +
                ", garden='" + garden + '\'' +
                '}';
    }
}
