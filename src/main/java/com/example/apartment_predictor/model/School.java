package com.example.apartment_predictor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String address;
    private String type;
    private String educationLevel;
    private String location;
    private int rating;
    private boolean isPublic;
    private double latitude;
    private double longitude;
    private Integer nearestNodeId;

    @ManyToMany(mappedBy = "nearbySchools")
    @JsonBackReference("property-schools")
    private List<Property> properties = new ArrayList<>();

    public School() {
    }

    public School(String name, String address, String type, String educationLevel,
                  String location, int rating, boolean isPublic,
                  double latitude, double longitude, Integer nearestNodeId) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.educationLevel = educationLevel;
        this.location = location;
        this.rating = rating;
        this.isPublic = isPublic;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nearestNodeId = nearestNodeId;
    }
}
