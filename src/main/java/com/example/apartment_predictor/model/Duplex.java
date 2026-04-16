package com.example.apartment_predictor.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Entity
@DiscriminatorValue("DUPLEX")
@Data
@EqualsAndHashCode(callSuper = true)
public class Duplex extends Property {

    private String name;

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
}
