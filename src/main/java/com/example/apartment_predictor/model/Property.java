package com.example.apartment_predictor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "property_type")
@Data
public abstract class Property {

    @Id
    protected String id;

    protected String address;
    protected Integer price;

    private double latitude;
    private double longitude;
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
}
