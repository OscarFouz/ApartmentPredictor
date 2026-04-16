package com.example.apartment_predictor.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Entity
@DiscriminatorValue("APARTMENT")
@Data
@EqualsAndHashCode(callSuper = true)
public class Apartment extends Property {

    private String name;
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

    public Apartment() {
        this.id = UUID.randomUUID().toString();
    }

    public Apartment(Integer price, Integer area, Integer bedrooms, Integer bathrooms, Integer stories,
                     String mainroad, String guestroom, String basement, String hotwaterheating,
                     String airconditioning, Integer parking, String prefarea, String furnishingstatus,
                     String name, String address) {
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
        this.name = name;
        this.address = address;
    }
}
