package com.example.apartment_predictor.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class PropertyContract {

    // ============================
    // ID
    // ============================
    @Id
    private String id;

    // ============================
    // CAMPOS
    // ============================
    private String contractName;
    private String contractDetails;
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
    @JoinColumn(name = "property_id")
    private Property property;

    // ============================
    // CONSTRUCTORES
    // ============================
    public PropertyContract() {
        this.id = UUID.randomUUID().toString();
    }

    public PropertyContract(String contractName, String contractDetails,
                            double agreedPrice, LocalDate startDate,
                            Owner owner, Property property) {
        this.id = UUID.randomUUID().toString();
        this.contractName = contractName;
        this.contractDetails = contractDetails;
        this.agreedPrice = agreedPrice;
        this.startDate = startDate;
        this.active = true;
        this.owner = owner;
        this.property = property;
    }

    // ============================
    // GETTERS
    // ============================
    public String getId() {return id;}
    public String getContractName() {return contractName;}
    public String getContractDetails() {return contractDetails;}
    public double getAgreedPrice() {return agreedPrice;}
    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}
    public boolean isActive() {return active;}
    public Owner getOwner() {return owner;}
    public Property getProperty() {return property;}

    // ============================
    // SETTERS
    // ============================
    public void setContractName(String contractName) {this.contractName = contractName;}
    public void setContractDetails(String contractDetails) {this.contractDetails = contractDetails;}
    public void setAgreedPrice(double agreedPrice) {this.agreedPrice = agreedPrice;}
    public void setStartDate(LocalDate startDate) {this.startDate = startDate;}
    public void setEndDate(LocalDate endDate) {this.endDate = endDate;}
    public void setActive(boolean active) {this.active = active;}
    public void setOwner(Owner owner) {this.owner = owner;}
    public void setProperty(Property property) {this.property = property;}

    // ============================
    // TO STRING
    // ============================
    @Override
    public String toString() {
        return "PropertyContract{" +
                "id='" + id + '\'' +
                ", contractName='" + contractName + '\'' +
                ", agreedPrice=" + agreedPrice +
                ", active=" + active +
                ", ownerId=" + (owner != null ? owner.getId() : "null") +
                ", propertyId=" + (property != null ? property.getId() : "null") +
                '}';
    }
}
