package com.example.apartment_predictor.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
@DiscriminatorValue("APARTMENT")
public class Apartment extends Property {

    // ============================
    // CAMPOS
    // ============================
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
    // CONSTRUCTORES
    // ============================
    public Apartment() {
        this.id = UUID.randomUUID().toString();
    }

    public Apartment(Integer price, Integer area, Integer bedrooms, Integer bathrooms, Integer stories,
                     String mainroad, String guestroom, String basement, String hotwaterheating,
                     String airconditioning, Integer parking, String prefarea, String furnishingstatus) {
        this.id = UUID.randomUUID().toString();
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
    // GETTERS
    // ============================
    public Integer getPrice() {return price;}
    public Integer getArea() {return area;}
    public Integer getBedrooms() {return bedrooms;}
    public Integer getBathrooms() {return bathrooms;}
    public Integer getStories() {return stories;}
    public String getMainroad() {return mainroad;}
    public String getGuestroom() {return guestroom;}
    public String getBasement() {return basement;}
    public String getHotwaterheating() {return hotwaterheating;}
    public String getAirconditioning() {return airconditioning;}
    public Integer getParking() {return parking;}
    public String getPrefarea() {return prefarea;}
    public String getFurnishingstatus() {return furnishingstatus;}

    // ============================
    // SETTERS
    // ============================
    public void setPrice(Integer price) {this.price = price;}
    public void setArea(Integer area) {this.area = area;}
    public void setBedrooms(Integer bedrooms) {this.bedrooms = bedrooms;}
    public void setBathrooms(Integer bathrooms) {this.bathrooms = bathrooms;}
    public void setStories(Integer stories) {this.stories = stories;}
    public void setMainroad(String mainroad) {this.mainroad = mainroad;}
    public void setGuestroom(String guestroom) {this.guestroom = guestroom;}
    public void setBasement(String basement) {this.basement = basement;}
    public void setHotwaterheating(String hotwaterheating) {this.hotwaterheating = hotwaterheating;}
    public void setAirconditioning(String airconditioning) {this.airconditioning = airconditioning;}
    public void setParking(Integer parking) {this.parking = parking;}
    public void setPrefarea(String prefarea) {this.prefarea = prefarea;}
    public void setFurnishingstatus(String furnishingstatus) {this.furnishingstatus = furnishingstatus;}

    // ============================
    // TO STRING
    // ============================
    @Override
    public String toString() {
        return "Apartment{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", area=" + area +
                ", bedrooms=" + bedrooms +
                ", bathrooms=" + bathrooms +
                ", stories=" + stories +
                '}';
    }
}
