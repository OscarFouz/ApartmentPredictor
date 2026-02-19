package com.example.apartment_predictor.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Integer price;
    private Integer area;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer stories;

    private String mainroad;
    private String guestroom;
    private String basement;
    private String hotwaterheating;
    private String airconditioning;

    private Integer parking;
    private String prefarea;
    private String furnishingstatus;

    // ============================
    // RELACIONES
    // ============================

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL)
    private List<PropertyContract> contracts = new ArrayList<>();

    // ============================
    // CONSTRUCTORES
    // ============================

    public Apartment() {
    }

    public Apartment(Integer price,
                     Integer area,
                     Integer bedrooms,
                     Integer bathrooms,
                     Integer stories,
                     String mainroad,
                     String guestroom,
                     String basement,
                     String hotwaterheating,
                     String airconditioning,
                     Integer parking,
                     String prefarea,
                     String furnishingstatus) {

        this.price = price;
        this.area = area;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.stories = stories;
        this.mainroad = mainroad;
        this.guestroom = guestroom;
        this.basement = basement;
        this.hotwaterheating = hotwaterheating;
        this.airconditioning = airconditioning;
        this.parking = parking;
        this.prefarea = prefarea;
        this.furnishingstatus = furnishingstatus;
    }

    // ============================
    // GETTERS & SETTERS
    // ============================

    public String getId() {
        return id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Integer getStories() {
        return stories;
    }

    public void setStories(Integer stories) {
        this.stories = stories;
    }

    public String getMainroad() {
        return mainroad;
    }

    public void setMainroad(String mainroad) {
        this.mainroad = mainroad;
    }

    public String getGuestroom() {
        return guestroom;
    }

    public void setGuestroom(String guestroom) {
        this.guestroom = guestroom;
    }

    public String getBasement() {
        return basement;
    }

    public void setBasement(String basement) {
        this.basement = basement;
    }

    public String getHotwaterheating() {
        return hotwaterheating;
    }

    public void setHotwaterheating(String hotwaterheating) {
        this.hotwaterheating = hotwaterheating;
    }

    public String getAirconditioning() {
        return airconditioning;
    }

    public void setAirconditioning(String airconditioning) {
        this.airconditioning = airconditioning;
    }

    public Integer getParking() {
        return parking;
    }

    public void setParking(Integer parking) {
        this.parking = parking;
    }

    public String getPrefarea() {
        return prefarea;
    }

    public void setPrefarea(String prefarea) {
        this.prefarea = prefarea;
    }

    public String getFurnishingstatus() {
        return furnishingstatus;
    }

    public void setFurnishingstatus(String furnishingstatus) {
        this.furnishingstatus = furnishingstatus;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<PropertyContract> getContracts() {
        return contracts;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id='" + id + '\'' +
                ", price=" + price +
                ", area=" + area +
                ", bedrooms=" + bedrooms +
                ", bathrooms=" + bathrooms +
                ", stories=" + stories +
                '}';
    }
}
