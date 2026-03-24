package com.example.apartment_predictor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "property_type")
public abstract class Property {

    @Id
    protected String id;

    protected String address;
    protected Integer price;

    // Coordenadas reales
    private double latitude;
    private double longitude;

    // Nodo más cercano en el grafo Manhattan (1..8)
    private Integer nearestNodeId;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties({"contracts", "apartments", "houses", "duplexes", "townHouses"})
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

    public Property() {}

    public String getId() {return id;}
    public String getAddress() {return address;}
    public Integer getPrice() {return price;}
    public Owner getOwner() {return owner;}
    public List<School> getNearbySchools() {return nearbySchools;}
    public List<PropertyContract> getPropertyContracts() {return propertyContracts;}
    public List<Review> getReviews() {return reviews;}
    public double getLatitude() {return latitude;}
    public double getLongitude() {return longitude;}
    public Integer getNearestNodeId() {return nearestNodeId;}

    public void setAddress(String address) {this.address = address;}
    public void setPrice(Integer price) {this.price = price;}
    public void setOwner(Owner owner) {this.owner = owner;}
    public void setNearbySchools(List<School> nearbySchools) {this.nearbySchools = nearbySchools;}
    public void setPropertyContracts(List<PropertyContract> propertyContracts) {this.propertyContracts = propertyContracts;}
    public void setReviews(List<Review> reviews) {this.reviews = reviews;}
    public void setLatitude(double latitude) {this.latitude = latitude;}
    public void setLongitude(double longitude) {this.longitude = longitude;}
    public void setNearestNodeId(Integer nearestNodeId) {this.nearestNodeId = nearestNodeId;}
}
