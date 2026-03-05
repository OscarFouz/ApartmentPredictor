package com.example.apartment_predictor.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.List;
import java.util.UUID;

@Entity
@DiscriminatorValue("DUPLEX")
public class Duplex extends Property {

    private String name;

    // ============================
    // CONSTRUCTORES
    // ============================
    public Duplex() {
        this.id = UUID.randomUUID().toString();
    }

    public Duplex(String id, String name, String address, Integer price, List<School> nearbySchools) {
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
        return "Duplex{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", id='" + id + '\'' +
                ", price=" + price +
                '}';
    }
}
