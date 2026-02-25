package com.example.apartment_predictor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "property_type")
public abstract class Property {

    // ============================
    // ID
    // ============================
    @Id
    protected String id;

    // ============================
    // CAMPOS
    // ============================
    protected String address;

    // ============================
    // RELACIONES
    // ============================
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference("owner-properties")
    protected Owner owner;

    @ManyToMany
    @JoinTable(
            name = "property_school",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "school_id")
    )
    @JsonManagedReference("property-schools")
    protected List<School> nearbySchools = new ArrayList<>();

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    @JsonManagedReference("property-contracts")
    protected List<PropertyContract> propertyContracts = new ArrayList<>();

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    @JsonManagedReference("property-reviews")
    private List<Review> reviews = new ArrayList<>();


    // ============================
    // CONSTRUCTORES
    // ============================
    public Property() {}

    // ============================
    // GETTERS
    // ============================
    public String getId() {return id;}
    public String getAddress() {return address;}
    public Owner getOwner() {return owner;}
    public List<School> getNearbySchools() {return nearbySchools;}
    public List<PropertyContract> getPropertyContracts() {return propertyContracts;}
    public List<Review> getReviews() {return reviews;}

    // ============================
    // SETTERS
    // ============================
    public void setAddress(String address) {this.address = address;}
    public void setOwner(Owner owner) {this.owner = owner;}
    public void setNearbySchools(List<School> nearbySchools) {this.nearbySchools = nearbySchools;}
    public void setPropertyContracts(List<PropertyContract> propertyContracts) {this.propertyContracts = propertyContracts;}
    public void setReviews(List<Review> reviews) {this.reviews = reviews;}

}
