package com.example.apartment_predictor.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Owner extends Person {

    // ============================
    // CAMPOS
    // ============================
    private boolean isBusiness;
    private String idLegalOwner;
    private LocalDate registrationDate;
    private int qtyDaysAsOwner;

    // ============================
    // RELACIONES
    // ============================
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<PropertyContract> contracts = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<House> houses = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Duplex> duplexes = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<TownHouse> townHouses = new ArrayList<>();

    // ============================
    // CONSTRUCTORES
    // ============================
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

    // ============================
    // GETTERS
    // ============================
    public boolean isBusiness() {return isBusiness;}
    public String getIdLegalOwner() {return idLegalOwner;}
    public LocalDate getRegistrationDate() {return registrationDate;}
    public int getQtyDaysAsOwner() {return qtyDaysAsOwner;}
    public List<PropertyContract> getContracts() {return contracts;}
    public List<House> getHouses() {return houses;}
    public List<Duplex> getDuplexes() {return duplexes;}
    public List<TownHouse> getTownHouses() {return townHouses;}

    // ============================
    // SETTERS
    // ============================
    public void setBusiness(boolean business) {isBusiness = business;}
    public void setIdLegalOwner(String idLegalOwner) {this.idLegalOwner = idLegalOwner;}
    public void setRegistrationDate(LocalDate registrationDate) {this.registrationDate = registrationDate;}
    public void setQtyDaysAsOwner(int qtyDaysAsOwner) {this.qtyDaysAsOwner = qtyDaysAsOwner;}
    public void setContracts(List<PropertyContract> contracts) {this.contracts = contracts;}
    public void setHouses(List<House> houses) {this.houses = houses;}
    public void setDuplexes(List<Duplex> duplexes) {this.duplexes = duplexes;}
    public void setTownHouses(List<TownHouse> townHouses) {this.townHouses = townHouses;}

    // ============================
    // TO STRING
    // ============================
    @Override
    public String toString() {
        return "Owner{" +
                "id='" + getId() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", isBusiness=" + isBusiness +
                ", idLegalOwner='" + idLegalOwner + '\'' +
                ", registrationDate=" + registrationDate +
                ", qtyDaysAsOwner=" + qtyDaysAsOwner +
                '}';
    }
}
