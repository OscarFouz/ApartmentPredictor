package com.example.apartment_predictor.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class PropertyContract {

    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
    private double agreedPrice;

    public PropertyContract() {}

    public PropertyContract(Owner owner, Apartment apartment, LocalDate startDate, double agreedPrice) {
        this.owner = owner;
        this.apartment = apartment;
        this.startDate = startDate;
        this.agreedPrice = agreedPrice;
        this.active = true;
    }

    public String getId() {
        return id;
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

    public double getAgreedPrice() {
        return agreedPrice;
    }

    public void setAgreedPrice(double agreedPrice) {
        this.agreedPrice = agreedPrice;
    }

    @Override
    public String toString() {
        return "PropertyContract{" +
                "id='" + id + '\'' +
                ", owner=" + (owner != null ? owner.getId() : null) +
                ", apartment=" + (apartment != null ? apartment.getId() : null) +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", active=" + active +
                ", agreedPrice=" + agreedPrice +
                '}';
    }
}
