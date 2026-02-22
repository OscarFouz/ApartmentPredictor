package com.example.apartment_predictor.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;

@MappedSuperclass
public abstract class Person {

    // ============================
    // ID
    // ============================
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // ============================
    // CAMPOS
    // ============================
    private String fullName;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private String password;
    private boolean isActive;

    // ============================
    // CONSTRUCTORES
    // ============================
    public Person() {
    }

    public Person(String fullName, LocalDate birthDate, String phone,
                  String email, String password, boolean isActive) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
    }

    // ============================
    // GETTERS
    // ============================
    public String getId() {return id;}
    public String getFullName() {return fullName; }
    public LocalDate getBirthDate() { return birthDate;}
    public String getPhone() {return phone;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public boolean isActive() {return isActive;}

    // ============================
    // SETTERS
    // ============================
    public void setFullName(String fullName) {this.fullName = fullName;}
    public void setBirthDate(LocalDate birthDate) {this.birthDate = birthDate;}
    public void setPhone(String phone) {this.phone = phone;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}
    public void setActive(boolean active) {isActive = active;}
}
