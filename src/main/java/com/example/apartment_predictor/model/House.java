package com.example.apartment_predictor.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String address;

    @OneToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToMany
    @JoinTable(
            name = "house_school",
            joinColumns = @JoinColumn(name = "house_id"),
            inverseJoinColumns = @JoinColumn(name = "school_id")
    )
    private List<School> nearbySchools = new ArrayList<>();

    public List<School> getNearbySchools() {
        return nearbySchools;
    }

    public void setNearbySchools(List<School> nearbySchools) {
        this.nearbySchools = nearbySchools;
    }


    public House() {
    }

    public House(String name, String address, Apartment apartment, Owner owner) {
        this.name = name;
        this.address = address;
        this.apartment = apartment;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "House{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
