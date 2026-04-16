package com.example.apartment_predictor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Owner extends Person {

    private boolean isBusiness;
    private String idLegalOwner;
    private LocalDate registrationDate;
    private int qtyDaysAsOwner;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonManagedReference("owner-contracts")
    private List<PropertyContract> contracts = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Apartment> apartments = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<House> houses = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Duplex> duplexes = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TownHouse> townHouses = new ArrayList<>();

    public Owner() {
        super();
    }

    public Owner(String fullName, String email, String phone) {
        super(fullName, null, phone, email, null, true);
        this.registrationDate = LocalDate.now();
    }

    public Owner(String fullName, LocalDate birthDate, String phone, String email,
                 String password, boolean isActive, boolean isBusiness,
                 String idLegalOwner, LocalDate registrationDate, int qtyDaysAsOwner) {
        super(fullName, birthDate, phone, email, password, isActive);
        this.isBusiness = isBusiness;
        this.idLegalOwner = idLegalOwner;
        this.registrationDate = registrationDate;
        this.qtyDaysAsOwner = qtyDaysAsOwner;
    }
}
