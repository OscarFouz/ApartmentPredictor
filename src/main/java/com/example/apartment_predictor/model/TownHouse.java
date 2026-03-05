package com.example.apartment_predictor.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.List;
import java.util.UUID;

@Entity
@DiscriminatorValue("TOWNHOUSE")
public class TownHouse extends Property {

    private String name;

    // ============================
    // CONSTRUCTORES
    // ============================
    public TownHouse() {
        this.id = UUID.randomUUID().toString();
    }

    public TownHouse(String id, String name, String address, Integer price, List<School> nearbySchools) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.price = price;
        this.nearbySchools = nearbySchools;
    }

    // ============================
    // GETTERS
    // ============================
    public String getName() { return name; }

    // ============================
    // SETTERS
    // ============================
    public void setName(String name) { this.name = name; }

    // ============================
    // TO STRING
    // ============================
    @Override
    public String toString() {
        return "TownHouse{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", id='" + id + '\'' +
                ", price=" + price +
                '}';
    }
}
