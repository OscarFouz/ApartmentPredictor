package com.example.apartment_predictor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Property {
    @Id
    protected String id = UUID.randomUUID().toString();
    protected int area;
    protected int locationRating;

    //Getters
    public int getArea() {
        return area;
    }

    public int getLocationRating() {
        return locationRating;
    }

    //Setters

    public void setArea(int area) {
        this.area = area;
    }

    public void setLocationRating(int locationRating) {
        this.locationRating = locationRating;
    }

    public abstract double calculatePrice();


}