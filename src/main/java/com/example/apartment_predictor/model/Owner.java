package com.example.apartment_predictor.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Owner extends Person {

    private String email;
    private String phone;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<PropertyContract> contracts = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<House> houses = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Duplex> duplexes = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<TownHouse> townHouses = new ArrayList<>();

    public Owner() {
    }

    public Owner(String name, String email, String phone) {
        super(name);
        this.email = email;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<PropertyContract> getContracts() {
        return contracts;
    }

    public List<House> getHouses() {
        return houses;
    }

    public List<Duplex> getDuplexes() {
        return duplexes;
    }

    public List<TownHouse> getTownHouses() {
        return townHouses;
    }

    public void addHouse(House house) {
        houses.add(house);
        house.setOwner(this);
    }

    public void addDuplex(Duplex duplex) {
        duplexes.add(duplex);
        duplex.setOwner(this);
    }

    public void addTownHouse(TownHouse townHouse) {
        townHouses.add(townHouse);
        townHouse.setOwner(this);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
