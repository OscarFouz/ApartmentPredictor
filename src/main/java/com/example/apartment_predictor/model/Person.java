package com.example.apartment_predictor.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    // ============================
    // CONSTRUCTOR VACÍO (OBLIGATORIO PARA JPA)
    // ============================
    public Person() {
    }

    // ============================
    // CONSTRUCTOR CON ARGUMENTOS
    // ============================
    public Person(String name) {
        this.name = name;
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


}
