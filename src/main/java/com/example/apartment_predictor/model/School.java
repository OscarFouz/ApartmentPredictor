package com.example.apartment_predictor.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class School {

    // ============================
    // ID
    // ============================
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // ============================
    // CAMPOS
    // ============================
    private String name;
    private String address;
    private String type;
    private String educationLevel;
    private String location;
    private int rating;
    private boolean isPublic;

    // ============================
    // RELACIONES
    // ============================
    @ManyToMany(mappedBy = "nearbySchools")
    private List<Property> properties = new ArrayList<>();

    // ============================
    // CONSTRUCTORES
    // ============================
    public School() {
    }

    public School(String name, String address, String type,
                  String educationLevel, String location,
                  int rating, boolean isPublic) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.educationLevel = educationLevel;
        this.location = location;
        this.rating = rating;
        this.isPublic = isPublic;
    }

    // ============================
    // GETTERS
    // ============================
    public String getId() {return id;}
    public String getName() {return name;}
    public String getAddress() {return address;}
    public String getType() {return type;}
    public String getEducationLevel() {return educationLevel;}
    public String getLocation() {return location;}
    public int getRating() {return rating;}
    public boolean isPublic() {return isPublic;}
    public List<Property> getProperties() {return properties;}

    // ============================
    // SETTERS
    // ============================
    public void setName(String name) {this.name = name;}
    public void setAddress(String address) {this.address = address;}
    public void setType(String type) {this.type = type;}
    public void setEducationLevel(String educationLevel) {this.educationLevel = educationLevel;}
    public void setLocation(String location) {this.location = location;}
    public void setRating(int rating) {this.rating = rating;}
    public void setPublic(boolean aPublic) {isPublic = aPublic;}
    public void setProperties(List<Property> properties) {this.properties = properties;}

    // ============================
    // TO STRING
    // ============================
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
                '}';
    }
}
