package com.example.apartment_predictor;

import jakarta.persistence.Entity;

@Entity // Indicates that this class is an entity in the data model
public class Reviewer {
    private String id;
    private String name;
    private String email;
    private int age;
    private boolean isOwner;
    private int numReviews;

    // Constructor
    public Reviewer() {
    }

    public Reviewer(String id, String name, String email, int age, boolean isOwner, int numReviews) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.isOwner = isOwner;
        this.numReviews = numReviews;
    }

    //Setters
    public void setName(String name) {this.name = name; }

    public void setEmail(String email) {this.email = email; }

    public void setAge(int age) {this.age = age; }

    public void setOwner(boolean owner) {isOwner = owner; }

    public void setNumReviews(int numReviews) {this.numReviews = numReviews; }

    //Getters

    public String getId() {return id; }

    public String getName() {return name; }

    public String getEmail() {return email; }

    public int getAge() {return age; }

    public boolean isOwner() {return isOwner; }

    public int getNumReviews() {return numReviews; }

    @Override
    public String toString() {
        return "Reviewer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", isOwner=" + isOwner +
                ", numReviews=" + numReviews +
                '}';
    }
}

