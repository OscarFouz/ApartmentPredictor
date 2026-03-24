package com.example.apartment_predictor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Datos básicos
    private String name;
    private String address;
    private String type;
    private String educationLevel;
    private String location;
    private int rating;
    private boolean isPublic;

    // Coordenadas reales
    private double latitude;
    private double longitude;

    // Nodo más cercano en el grafo Manhattan (1..8)
    private Integer nearestNodeId;

    @ManyToMany(mappedBy = "nearbySchools")
    @JsonBackReference("property-schools")
    private List<Property> properties = new ArrayList<>();

    public School() {
    }

    public School(String name,
                  String address,
                  String type,
                  String educationLevel,
                  String location,
                  int rating,
                  boolean isPublic,
                  double latitude,
                  double longitude,
                  Integer nearestNodeId) {
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

    public String getId() {return id;}
    public String getName() {return name;}
    public String getAddress() {return address;}
    public String getType() {return type;}
    public String getEducationLevel() {return educationLevel;}
    public String getLocation() {return location;}
    public int getRating() {return rating;}
    public boolean isPublic() {return isPublic;}
    public double getLatitude() {return latitude;}
    public double getLongitude() {return longitude;}
    public Integer getNearestNodeId() {return nearestNodeId;}
    public List<Property> getProperties() {return properties;}

    public void setName(String name) {this.name = name;}
    public void setAddress(String address) {this.address = address;}
    public void setType(String type) {this.type = type;}
    public void setEducationLevel(String educationLevel) {this.educationLevel = educationLevel;}
    public void setLocation(String location) {this.location = location;}
    public void setRating(int rating) {this.rating = rating;}
    public void setPublic(boolean aPublic) {isPublic = aPublic;}
    public void setLatitude(double latitude) {this.latitude = latitude;}
    public void setLongitude(double longitude) {this.longitude = longitude;}
    public void setNearestNodeId(Integer nearestNodeId) {this.nearestNodeId = nearestNodeId;}
    public void setProperties(List<Property> properties) {this.properties = properties;}

    @Override
    public String toString() {
        return "School{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", type='" + type + '\'' +
                ", educationLevel='" + educationLevel + '\'' +
                ", location='" + location + '\'' +
                ", rating=" + rating +
                ", isPublic=" + isPublic +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", nearestNodeId=" + nearestNodeId +
                '}';
    }
}
