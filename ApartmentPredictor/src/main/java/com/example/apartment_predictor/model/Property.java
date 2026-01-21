package com.example.apartment_predictor.model;

@MappedSuperclass
public abstract class Property {
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