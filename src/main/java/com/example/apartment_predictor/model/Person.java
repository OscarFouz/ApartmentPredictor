package com.example.apartment_predictor.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String fullName;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private String password;
    private boolean isActive;
    private String role;

    public Person() {}

    public Person(String fullName, LocalDate birthDate, String phone,
                  String email, String password, boolean isActive) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
    }
}
