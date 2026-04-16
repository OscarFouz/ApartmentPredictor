package com.example.apartment_predictor.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Entity
@DiscriminatorValue("HOUSE")
@Data
@EqualsAndHashCode(callSuper = true)
public class House extends Property {

    private String name;

    public House() {
        this.id = UUID.randomUUID().toString();
    }

    public House(String name, String address, Integer price) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.price = price;
    }
}
