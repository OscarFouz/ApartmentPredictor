package com.example.apartment_predictor.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class PropertyContract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private double agreedPrice;

    private LocalDate startDate;
    private LocalDate endDate;

    private boolean active;

    // ============================
    // RELACIONES
    // ============================

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne
    @JoinColumn(name = "duplex_id")
    private Duplex duplex;

    @ManyToOne
    @JoinColumn(name = "townhouse_id")
    private TownHouse townHouse;

    // ============================
    // CONSTRUCTORES
    // ============================

    public PropertyContract() {}

    public PropertyContract(double agreedPrice, LocalDate startDate, LocalDate endDate, boolean active) {
        this.agreedPrice = agreedPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
    }

    // ============================
    // GETTERS & SETTERS
    // ============================

    public String getId() {
        return id;
    }

    public double getAgreedPrice() {
        return agreedPrice;
    }

    public void setAgreedPrice(double agreedPrice) {
        this.agreedPrice = agreedPrice;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Duplex getDuplex() {
        return duplex;
    }

    public void setDuplex(Duplex duplex) {
        this.duplex = duplex;
    }

    public TownHouse getTownHouse() {
        return townHouse;
    }

    public void setTownHouse(TownHouse townHouse) {
        this.townHouse = townHouse;
    }

    // ============================
    // TO STRING (SEGURO)
    // ============================

    @Override
    public String toString() {
        return "PropertyContract{" +
                "id='" + id + '\'' +
                ", agreedPrice=" + agreedPrice +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", active=" + active +
                '}';
    }
}
