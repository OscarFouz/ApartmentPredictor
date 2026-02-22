package com.example.apartment_predictor.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
@DiscriminatorValue("HOUSE")
public class House extends Property {

    // ============================
    // CAMPOS
    // ============================
    private String name;
    // Usa el address heredado de Property


    // ============================
    // CONSTRUCTORES
    // ============================
    public House() {
        this.id = UUID.randomUUID().toString();
    }

    public House(String name, String address, Owner owner) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.owner = owner;
    }

    // ============================
    // GETTERS
    // ============================
    public String getName() {return name;}

    // ============================
    // SETTERS
    // ============================
    public void setName(String name) {this.name = name;}

    // ============================
    // TO STRING
    // ============================
    @Override
    public String toString() {
        return "House{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", addressLocal='" + address + '\'' +
                '}';
    }
}
