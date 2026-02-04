package com.example.apartment_predictor.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "property_type")
public abstract class Property {

    @Id
    protected String id = UUID.randomUUID().toString();

    protected int area;
    protected int locationRating;

    public String getId() {
        return id;
    }

    public int getArea() {
        return area;
    }

    public int getLocationRating() {
        return locationRating;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public void setLocationRating(int locationRating) {
        this.locationRating = locationRating;
    }

    public abstract double calculatePrice();
}
