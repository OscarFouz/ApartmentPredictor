package com.example.apartment_predictor.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String address;

    private String type;            // público, privado, concertado
    private String educationLevel;  // infantil, primaria, secundaria...

    private double latitude;
    private double longitude;

    private double rating;
    private int studentCount;

    // ============================
    // RELACIONES MANY-TO-MANY
    // ============================

    @ManyToMany(mappedBy = "nearbySchools")
    private List<Apartment> apartments = new ArrayList<>();

    @ManyToMany(mappedBy = "nearbySchools")
    private List<House> houses = new ArrayList<>();

    @ManyToMany(mappedBy = "nearbySchools")
    private List<Duplex> duplexes = new ArrayList<>();

    @ManyToMany(mappedBy = "nearbySchools")
    private List<TownHouse> townHouses = new ArrayList<>();

    // ============================
    // CONSTRUCTORES
    // ============================

    public School() {}

    public School(String name, String address, String type, String educationLevel,
                  double latitude, double longitude, double rating, int studentCount) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.educationLevel = educationLevel;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.studentCount = studentCount;
    }

    // ============================
    // GETTERS & SETTERS
    // ============================

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public List<Apartment> getApartments() {
        return apartments;
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

    @Override
    public String toString() {
        return "School{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", educationLevel='" + educationLevel + '\'' +
                ", rating=" + rating +
                '}';
    }
}
